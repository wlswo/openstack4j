package com.oidev.openstack4j.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDto {

    private String id;
    private String name;
    private String containerFormat;
    private String visibility;
    private String diskFormat;
    private Long minDisk;
    private Long minRam;
    private String url;

    @Override
    public String toString() {
        return "ImageVo{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", containerFormat='" + containerFormat + '\'' +
            ", visibility='" + visibility + '\'' +
            ", diskFormat='" + diskFormat + '\'' +
            ", minDisk=" + minDisk +
            ", minRam=" + minRam +
            ", url='" + url + '\'' +
            '}';
    }
}
