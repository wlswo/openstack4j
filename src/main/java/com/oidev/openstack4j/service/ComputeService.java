package com.oidev.openstack4j.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.oidev.openstack4j.entity.FlavorDto;
import com.oidev.openstack4j.entity.InstanceDto;
import com.oidev.openstack4j.entity.OsClientV3;
import lombok.RequiredArgsConstructor;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author : 변재진
 * @version : 1.0.0
 * @description : Compute Api Service Class
 **/
@Service
@RequiredArgsConstructor
public class ComputeService {

    private static final Logger log = LoggerFactory.getLogger(ComputeService.class);
    private final int MAX_WAIT_TIME = 2000;

    private final OsClientV3 osClientV3;

    /**
     * @param : X
     * @return : instances
     * @description : 인스턴스 엔티티들을 List 에 담아 반환 합니다.
     **/
    public List<? extends Server> getInstances() {
        return osClientV3.getOS().compute().servers().list();
    }

    /**
     * @param : status
     * @return : instances
     * @description : 인스턴스의 상태를 인자로 받습니다. 해당 상태인 인스턴스들을 List에 담아 반환 합니다.
     **/
    public List<? extends Server> getInstancesByStatus(String STATUS) {
        Map<String, String> filter = new HashMap<>();
        filter.put("status", STATUS);
        return osClientV3.getOS().compute().servers().list(filter);
    }

    /**
     * @param : instanceId
     * @return : instance
     * @description : 인스턴스 ID를 받아 해당 인스턴스를 반환 합니다.
     **/
    public Server getInstance(String instanceId) {
        return osClientV3.getOS().compute().servers().get(instanceId);
    }

    /**
     * @param : instanceDto
     * @return : instance
     * @description : 인자값들의 정보로 인스턴스를 생성합니다. 정상 생성된 인스턴스를 반환합니다.
     **/
    public Server createInstance(InstanceDto instanceDto) {
        ServerCreate sc = Builders.server()
            .name(instanceDto.getServerName())
            .flavor(instanceDto.getFlavor())
            .image(instanceDto.getImage())
            .networks(instanceDto.getNetwork())
            .build();

        OSClient.OSClientV3 os = osClientV3.getOS();

        Server server = os.compute().servers().bootAndWaitActive(sc, MAX_WAIT_TIME);
        os.compute().servers()
            .waitForServerStatus(server.getId(), Server.Status.ACTIVE, MAX_WAIT_TIME,
                TimeUnit.MILLISECONDS);
        return server;
    }

    /**
     * @param : instanceId
     * @return : ActionResponse
     * @description : 인스턴스 ID를 인자로 받아 해당 인스턴스를 삭제합니다. 인스턴스 삭제 작업의 상태 ActionResponse를 반환합니다.
     **/
    public ActionResponse deleteInstance(String instanceId) {
        ActionResponse status = osClientV3.getOS().compute().servers().delete(instanceId);
        return status;
    }

    /**
     * @param : instanceId, stats
     * @return : ActionResponse
     * @description : instanceId, statu를 인자로받아 인스턴스의 상태를 변경합니다. 인스턴스 상태 변경 작업의 대한 결과 ActionResponse를
     * 반환합니다.
     **/
    public ActionResponse changeStatusInstance(String instanceId, String status) {
        System.out.println(status);
        return osClientV3.getOS().compute().servers().action(instanceId, Action.valueOf(status));
    }


    /**
     * @param : flavor Value Object
     * @return : flavor
     * @description : Flavor 엔티티를 받아 Flavor를 생성합니다.
     **/
    public Flavor createFlavor(FlavorDto flavorDto) {
        Flavor flavor = Builders.flavor()
            .name(flavorDto.getName())
            .ram(flavorDto.getRam())
            .vcpus(flavorDto.getVcpus())
            .disk(flavorDto.getDisk())
            .rxtxFactor(flavorDto.getRxtxFactor())
            .build();
        return osClientV3.getOS().compute().flavors().create(flavor);
    }

    /**
     * @param :
     * @return : flavor List
     * @description : 존재하는 Flavor들을 반환합니다.
     **/
    public List<? extends Flavor> getFlavors() {
        return osClientV3.getOS().compute().flavors().list();
    }
}
