package com.oidev.openstack4j.entity;

import lombok.Getter;

@Getter
public class FlavorDto {

    String id;
    String name;
    int ram;
    int vcpus;
    int disk;
    float rxtxFactor;

    @Override
    public String toString() {
        return "Flavor{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", ram=" + ram +
            ", vcpus=" + vcpus +
            ", disk=" + disk +
            ", rxtxFactor=" + rxtxFactor +
            '}';
    }
}
