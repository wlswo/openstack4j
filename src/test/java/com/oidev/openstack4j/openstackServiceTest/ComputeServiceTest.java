package com.oidev.openstack4j.openstackServiceTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@PropertySource("classpath:application.properties")
public class ComputeServiceTest {

    @Autowired
    private Config config;
    public OSClientV3 os;
    protected String SERVER_ID = "";

    @BeforeEach
    void initConfig() {
        os = config.getOs();
        assertThat(os.getToken().getId()).isNotNull();
    }

    @Test
    @DisplayName("List all Servers")
    void getServerList() {
        List<? extends Server> servers = os.compute().servers().list();
        assertThat(servers).isNotNull();
    }

    @Test
    @DisplayName("Server Create")
    void createServer() {
        ServerCreate sc = Builders.server()
                .name("openstack4j-test-server")
                .flavor("1")
                .image("2d6e787f-4e57-41ac-a1e0-d1eacbe59b75")
                .build();
        Server server = os.compute().servers().bootAndWaitActive(sc, 2000);

        assertThat(server.getStatus()).isEqualTo("ACTIVE");
        SERVER_ID = server.getId();
    }

    @Test
    @DisplayName("Server Delete")
    void deleteServer() {
        int status = os.compute().servers().delete(SERVER_ID).getCode();
        assertThat(status).isEqualTo(200);
    }

}
