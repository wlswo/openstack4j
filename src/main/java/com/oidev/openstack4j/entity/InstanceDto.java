package com.oidev.openstack4j.entity;


import lombok.Getter;

import java.util.List;

@Getter
public class InstanceDto {

    private String id;
    private String serverName;
    private String image;
    private List<String> network;
    private String flavor;

    @Override
    public String toString() {
        return "Instance{" +
            "id='" + id + '\'' +
            ", serverName='" + serverName + '\'' +
            ", image='" + image + '\'' +
            ", network=" + network +
            ", flavor='" + flavor + '\'' +
            '}';
    }
}
