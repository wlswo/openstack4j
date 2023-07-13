package com.oidev.openstack4j.config;

import lombok.Getter;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Getter
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

    public OSClientV3 os;

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

    @PostConstruct
    public void InjectionOSClientV3() {
        this.os = OSFactory.builderV3()
                .endpoint(this.AUTH_END_POINT)
                .credentials(this.ID, this.PASSWORD, Identifier.byId(this.DOMAIN_NAME))
                .scopeToProject(Identifier.byName(this.PROJECT_NAME), Identifier.byId(this.DOMAIN_NAME))
                .authenticate();
    }

    public OSClientV3 issueToken() {
        return OSFactory.builderV3()
                .endpoint(this.AUTH_END_POINT)
                .credentials(this.ID, this.PASSWORD, Identifier.byId(this.DOMAIN_NAME))
                .scopeToProject(Identifier.byName(this.PROJECT_NAME), Identifier.byId(this.DOMAIN_NAME))
                .authenticate();
    }
}
