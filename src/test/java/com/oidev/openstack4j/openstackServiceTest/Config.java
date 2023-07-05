package com.oidev.openstack4j.openstackServiceTest;

import lombok.Getter;
import lombok.Setter;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter @Setter
public class Config {

    @Value("${openstack.id}")
    private String ID;
    @Value("${openstack.password}")
    private String PASSWORD;
    @Value("${openstack.auth_end_point}")
    private String AUTH_END_POINT;
    @Value("${openstack.domain_name}")
    private String DOMAIN_NAME;
    @Value("${openstack.project_name}")
    private String PROJECT_NAME;

    private OSClientV3 os;

    @Override
    public String toString() {
        return "Config{" +
                "ID='" + ID + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", AUTH_END_POINT='" + AUTH_END_POINT + '\'' +
                ", DOMAIN_NAME='" + DOMAIN_NAME + '\'' +
                ", PROJECT_NAME='" + PROJECT_NAME + '\'' +
                '}';
    }

    public Config() {
        System.out.println(AUTH_END_POINT);
        this.os = OSFactory.builderV3()
                .endpoint(AUTH_END_POINT)
                .credentials(ID, PASSWORD, Identifier.byId(DOMAIN_NAME))
                .scopeToProject(Identifier.byName(PROJECT_NAME), Identifier.byId(DOMAIN_NAME))
                .authenticate();
    }
}
