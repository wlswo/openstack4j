package com.oidev.openstack4j.openstackServiceTest;

import org.junit.jupiter.api.*;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.identity.v3.Domain;
import org.openstack4j.model.identity.v3.Project;
import org.openstack4j.model.identity.v3.Service;
import org.openstack4j.model.identity.v3.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IdentityServiceTest {
    @Autowired
    private TestConfig testConfig;
    public OSClient.OSClientV3 os;
    private final String PROJECT_ID = "bc4612ce02724d01bf0004a50ac3c0ba";
    private final String USER_ID = "9848f49403b54f12ba1b7fadac356358";
    private final String ROLE_ID = "5cec53d8ba964d0ba1bab618c7ae3e2e";

    @BeforeAll
    void initConfig() {
        os = testConfig.getOS();
        assertThat(os.getToken()).isNotNull();
    }

    @Test
    @Order(10)
    void validatingToken() {
        // validate and show details for another token
        Token token = os.identity().tokens().get(os.getToken().getId());
        System.out.println(token);

        // validate another token
        ActionResponse validateToken = os.identity().tokens().check(os.getToken().getId());
        assertThat(validateToken.isSuccess()).isTrue();
    }

    @Test
    @Order(20)
    void getServiceCatalogTest() {
        // get service catalog for
        List<? extends Service> serviceCatalog = os.identity().tokens().getServiceCatalog(os.getToken().getId());
        serviceCatalog.forEach(service -> System.out.println(service));
    }

    @Test
    @Order(30)
    void getAvailableScopeToken() {
        // get available project scopes
        List<? extends Project> availableProjectScopes = os.identity().tokens().getProjectScopes(os.getToken().getId());
        availableProjectScopes.forEach(project -> System.out.println(project));

        // get available domain scopes
        List<? extends Domain> availableDomainScopes = os.identity().tokens().getDomainScopes(os.getToken().getId());
        availableDomainScopes.forEach(domain -> System.out.println(domain));

    }

    @Test
    @Order(100)
    void checkProjectRole() {
        ActionResponse checkProjectRole = os.identity().roles().checkProjectUserRole(PROJECT_ID, USER_ID, ROLE_ID);
        System.out.println(checkProjectRole);
    }
}
