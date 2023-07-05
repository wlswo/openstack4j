package com.oidev.openstack4j.identityServiceTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.Link;
import org.openstack4j.model.identity.v3.User;
import org.openstack4j.openstack.OSFactory;
import org.openstack4j.openstack.identity.v3.domain.KeystoneUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Configuration
@PropertySource("classpath:application.properties")
public class IdentityServiceTest {

    @Value("${openstack.id}")
    private String ID;
    @Value("${openstack.password}")
    private String PASSWORD;
    @Value("${openstack.auth_end_point}")
    private String AUTH_END_POINT;
    @Value("${openstack.domain_name}")
    private String DOMAIN_NAME = "default";
    @Value("${openstack.project_name}")
    private String PROJECT_NAME;

    public OSClientV3 os;

    @Test
    void initConfig() {
        os = OSFactory.builderV3()
                .endpoint(AUTH_END_POINT)
                .credentials(ID, PASSWORD, Identifier.byId(DOMAIN_NAME))
                .scopeToProject(Identifier.byName(PROJECT_NAME), Identifier.byId(DOMAIN_NAME))
                .authenticate();
    }
}
