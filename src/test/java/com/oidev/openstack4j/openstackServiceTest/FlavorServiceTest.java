package com.oidev.openstack4j.openstackServiceTest;

import org.junit.jupiter.api.*;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://openstack4j.github.io/learn/compute/#flavors
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlavorServiceTest {

    @Autowired
    private TestConfig testConfig;
    public OSClient.OSClientV3 os;
    private final String FLAVOR_NAME = "test_flavor";

    @BeforeEach
    void initConfig() {
        os = testConfig.getOS();
        assertThat(os.getToken()).isNotNull();
    }

    @Test
    @DisplayName("List all Flavor")
    @Order(1)
    void getFlavorList() {
        List<? extends Flavor> flavors = os.compute().flavors().list();
        assertThat(flavors).isNotNull();
    }

    @Test
    @DisplayName("Create Flavor")
    @Order(2)
    void createFlavor() {
        Flavor flavor = Builders.flavor()
                .name(FLAVOR_NAME)
                .ram(1024)
                .vcpus(1)
                .build();
        Flavor f = os.compute().flavors().create(flavor);
        assertThat(f.getName()).isEqualTo(FLAVOR_NAME);
        assertThat(f.getVcpus()).isEqualTo(1);
        assertThat(f.getRam()).isEqualTo(1024);
    }

    @Test
    @DisplayName("delete Flavor")
    @Order(3)
    void deleteFlavor() {
        Flavor flavor = os.compute().flavors().list().stream()
                .filter(f -> f.getName().equals(FLAVOR_NAME))
                .findFirst()
                .orElseThrow(() -> new NotFoundException());

        assertThat(os.compute().flavors().delete(flavor.getId()).isSuccess()).isTrue();
    }
}
