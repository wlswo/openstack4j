package com.oidev.openstack4j.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.oidev.openstack4j.entity.ImageDto;
import com.oidev.openstack4j.entity.OsClientV3;
import lombok.RequiredArgsConstructor;
import org.openstack4j.api.Builders;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.image.v2.ContainerFormat;
import org.openstack4j.model.image.v2.DiskFormat;
import org.openstack4j.model.image.v2.Image;
import org.openstack4j.model.image.v2.Image.ImageVisibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);
    private final OsClientV3 osClientV3;

    /**
     * @param : ImageVo
     * @return : Image
     * @description : 이름, 컨테이너 포맷, 디스크 포맷, 디스크용량, 램 정보를 받아 이미지 메타데이터를 생성합니다. 생성된 이미지 정보를 반환합니다.
     **/
    public Image createImage(ImageDto imageDto) throws MalformedURLException {
        // step 1. 이미지 메타데이터 생성
        Image image = osClientV3.getOS().imagesV2().create(
            Builders.imageV2()
                .name(imageDto.getName())
                .containerFormat(ContainerFormat.value(imageDto.getContainerFormat()))
                .visibility(ImageVisibility.forValue(imageDto.getVisibility()))
                .diskFormat(DiskFormat.value(imageDto.getDiskFormat()))
                .minDisk(imageDto.getMinDisk())
                .minRam(imageDto.getMinRam())
                .build());

        log.info(imageDto.getUrl());
        log.info(image.getId());

        // step 2. payload 생성
        Payload<URL> payload = Payloads.create(
            new URL(imageDto.getUrl()));

        // step 3. 이미지 업로드
        ActionResponse upload = osClientV3.getOS().imagesV2().upload(
            image.getId(),
            payload,
            image);

        return image;
    }

    /**
     * @param : X
     * @return : images
     * @description : 생성되어 있는 모든 이미지들의 정보를 반환합니다.
     **/
    public List<? extends Image> getImageList() {
        return osClientV3.getOS().imagesV2().list();
    }

    /**
     * @param : imageId
     * @return : Image
     * @description : 인자로 받은 Id에 해당하는 이미지의 정보를 반환합니다.
     **/
    public Image getImage(String imageId) {
        return osClientV3.getOS().imagesV2().get(imageId);
    }

    /**
     * @param : imageId
     * @return : status code
     * @description : 인자로 받은 Id에 해당하는 이미지를 활성화 합니다. 작업의 상태정보를 반환합니다.
     **/
    public ActionResponse activateImage(String imageId) {
        return osClientV3.getOS().imagesV2().reactivate(imageId);
    }

    /**
     * @param : imageId
     * @return : status code
     * @description : 인자로 받은 Id에 해당하는 이미지를 비활성화 합니다. 작업의 상태정보를 반환합니다.
     **/
    public ActionResponse deactivateImage(String imageId) {
        return osClientV3.getOS().imagesV2().deactivate(imageId);
    }
}
