package com.oidev.openstack4j.service;

import java.util.List;

import com.oidev.openstack4j.entity.OsClientV3;
import lombok.RequiredArgsConstructor;
import org.openstack4j.model.storage.block.Volume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolumeService {

    private static final Logger log = LoggerFactory.getLogger(VolumeService.class);
    private final OsClientV3 osClientV3;

    /**
     * @param : X
     * @return : volumes
     * @description : 존재하는 볼륨 리스트를 반환합니다.
     **/
    public List<? extends Volume> getVolumes() {
        return osClientV3.getOS().blockStorage().volumes().list();
    }

    /**
     * @param : volumeId
     * @return : volume
     * @description : volumeId에 해당하는 볼륨을 반환합니다.
     **/
    public Volume getVolume(String volumeId) {
        return osClientV3.getOS().blockStorage().volumes().get(volumeId);
    }
}
