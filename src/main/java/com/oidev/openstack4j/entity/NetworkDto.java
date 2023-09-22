package com.oidev.openstack4j.entity;

import lombok.Getter;

@Getter
public class NetworkDto {

    String serverId;
    String networkId;
    String portId;
    String floatingNetworkId;

    @Override
    public String toString() {
        return "NetworkVo{" +
            "serverId='" + serverId + '\'' +
            ", networkId='" + networkId + '\'' +
            ", portId='" + portId + '\'' +
            ", floatingNetworkId='" + floatingNetworkId + '\'' +
            '}';
    }
}
