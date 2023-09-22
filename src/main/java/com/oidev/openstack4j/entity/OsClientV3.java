package com.oidev.openstack4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class OsClientV3 {

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

    public OSClient.OSClientV3 getOS() {
        return OSFactory.builderV3()
                .endpoint(this.AUTH_END_POINT)
                .credentials(this.ID, this.PASSWORD, Identifier.byId(this.DOMAIN_NAME))
                .scopeToProject(Identifier.byName(this.PROJECT_NAME), Identifier.byId(this.DOMAIN_NAME))
                .authenticate();
    }
}
