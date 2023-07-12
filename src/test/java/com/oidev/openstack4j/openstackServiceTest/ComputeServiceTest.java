package com.oidev.openstack4j.openstackServiceTest;

import org.junit.jupiter.api.*;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ComputeServiceTest {

    @Autowired
    private Config config;
    public OSClient.OSClientV3 os;
    private final String SERVER_NAME = "openstack4j-test-server";
    private final String NETWORK_ID = "475a7608-e09f-475a-b584-2c37a7f67d1c";
    private final String IMAGE_ID = "2d6e787f-4e57-41ac-a1e0-d1eacbe59b75";
    private final String FLAVOR_ID = "1";
    private final int MAX_WAIT_TIME = 2000;
    private final int SUCCESS_STATUS = 200;

    private String SERVER_ID = "";

    @BeforeAll
    void initConfig() {
        os = config.getOS();
        assertThat(os.getToken()).isNotNull();
    }

    @Test
    @DisplayName("List all Servers")
    @Order(1)
    void getServerList() {
        List<? extends Server> servers = os.compute().servers().list();
        assertThat(servers).isNotNull();
    }

    @Test
    @DisplayName("Server Create")
    @Order(2)
    void createServer() {
        List<String> networks = new ArrayList<>();
        networks.add(NETWORK_ID);

        ServerCreate sc = Builders.server()
                .name(SERVER_NAME)
                .flavor(FLAVOR_ID)
                .image(IMAGE_ID)
                .networks(networks)
                .build();

        Server server = os.compute().servers().bootAndWaitActive(sc, MAX_WAIT_TIME);
        os.compute().servers().waitForServerStatus(server.getId(), Server.Status.ACTIVE, MAX_WAIT_TIME, TimeUnit.MILLISECONDS);
        SERVER_ID = server.getId();

        assertThat(server.getStatus()).isEqualTo(Server.Status.BUILD);
    }

    @Test
    @DisplayName("Stop Server")
    @Order(3)
    void stopServer() {
        List<? extends Server> servers = os.compute().servers().list();
        Server s = servers.stream().filter(server -> server.getId().equals(SERVER_ID)).findAny().orElseThrow(() -> new NotFoundException());
        assertThat(os.compute().servers().action(s.getId(), Action.STOP).isSuccess()).isEqualTo(true);
        os.compute().servers().waitForServerStatus(s.getId(), Server.Status.STOPPED, MAX_WAIT_TIME, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("Start Server")
    @Order(4)
    void startServer() {
        List<? extends Server> servers = os.compute().servers().list();
        Server s = servers.stream().filter(server -> server.getId().equals(SERVER_ID)).findAny().orElseThrow(() -> new NotFoundException());
        os.compute().servers().action(s.getId(), Action.START).isSuccess();
        os.compute().servers().waitForServerStatus(s.getId(), Server.Status.ACTIVE, MAX_WAIT_TIME, TimeUnit.MILLISECONDS);
        assertThat(s.getStatus()).isEqualTo(Server.Status.ACTIVE);
    }

    @Test
    @DisplayName("Server Delete")
    @Order(5)
    void deleteServer() {
        List<? extends Server> servers = os.compute().servers().list();
        Server server = servers.stream().filter(s -> s.getId().equals(SERVER_ID)).findAny().orElseThrow(() -> new NotFoundException());
        ActionResponse status = os.compute().servers().delete(server.getId());
        assertThat(status.getCode()).isEqualTo(SUCCESS_STATUS);
    }
}