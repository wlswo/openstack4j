package com.oidev.openstack4j.openstackServiceTest;


import org.junit.jupiter.api.*;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.identity.v3.Tenant;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@PropertySource("classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlavorServiceTest {

    @Autowired
    private Config config;
    public OSClient.OSClientV3 os;
    private final String NETWORK_NAME = "test_network";

    @BeforeEach
    void initConfig() {
        os = config.getOS();
        assertThat(os.getToken()).isNotNull();
    }

    @Test
    @DisplayName("List all Networks")
    @Order(1)
    void getNetworksList() {
        List<? extends Network> networks = os.networking().network().list();
        assertThat(networks).isNotNull();
    }

    @Test
    @DisplayName("Create Network")
    @Order(2)
    void createNetwork() {
        Network network = os.networking().network()
                .create(Builders.network().name(NETWORK_NAME).adminStateUp(true).isRouterExternal(true).build());
        assertThat(network.getName()).isEqualTo(NETWORK_NAME);
        assertThat(network.getStatus()).isEqualTo(State.ACTIVE);
    }

    @Test
    @DisplayName("Delete Network")
    @Order(3)
    void deleteNetwork() {
        Network n = os.networking().network().list().stream()
                .filter(network -> network.getName().equals(NETWORK_NAME))
                .findFirst()
                .orElseThrow(() -> new NotFoundException());

        assertThat(os.networking().network().delete(n.getId()).isSuccess()).isEqualTo(true);
    }
}
