package com.oidev.openstack4j.openstackServiceTest;


import org.junit.jupiter.api.*;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@PropertySource("classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComputeServiceTest {

    @Autowired
    private Config config;
    public OSClientV3 os;
    private final String SERVER_NAME = "openstack4j-test-server";

    @BeforeEach
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
        networks.add("475a7608-e09f-475a-b584-2c37a7f67d1c");

        ServerCreate sc = Builders.server()
                .name(SERVER_NAME)
                .flavor("1")
                .image("2d6e787f-4e57-41ac-a1e0-d1eacbe59b75")
                .networks(networks)
                .build();
        Server server = os.compute().servers().bootAndWaitActive(sc, 1000);
        os.compute().servers().waitForServerStatus(server.getId(), Server.Status.ACTIVE, 2000, TimeUnit.MILLISECONDS);
        assertThat(server.getStatus()).isEqualTo(Server.Status.BUILD);
    }

    @Test
    @DisplayName("Stop Server")
    @Order(3)
    void stopServer() {
        List<? extends Server> servers = os.compute().servers().list();
        Server s = servers.stream().filter(server -> server.getName().equals(SERVER_NAME)).findFirst().orElseThrow(() -> new NotFoundException());
        assertThat(os.compute().servers().action(s.getId(), Action.STOP).isSuccess()).isEqualTo(true);
        os.compute().servers().waitForServerStatus(s.getId(), Server.Status.STOPPED, 2000, TimeUnit.MILLISECONDS);

    }

    @Test
    @DisplayName("Start Server")
    @Order(4)
    void startServer() {
        List<? extends Server> servers = os.compute().servers().list();
        Server s = servers.stream().filter(server -> server.getName().equals(SERVER_NAME)).findFirst().orElseThrow(() -> new NotFoundException());
        os.compute().servers().action(s.getId(), Action.START);
        os.compute().servers().waitForServerStatus(s.getId(), Server.Status.ACTIVE, 2000, TimeUnit.MILLISECONDS);

        assertThat(s.getStatus()).isEqualTo(Server.Status.ACTIVE);
    }

    @Test
    @DisplayName("Server Delete")
    @Order(5)
    void deleteServer() {

        List<? extends Server> servers = os.compute().servers().list();
        Server server = servers.stream().filter(s ->
                s.getName().equals(SERVER_NAME)
        ).findFirst().orElseThrow(() -> new NotFoundException());

        ActionResponse status = os.compute().servers().delete(server.getId());
        assertThat(status.getCode()).isEqualTo(200);
    }
}
