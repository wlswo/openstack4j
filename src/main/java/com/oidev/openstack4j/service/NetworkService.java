package com.oidev.openstack4j.service;

import java.util.List;

import com.oidev.openstack4j.entity.NetworkDto;
import com.oidev.openstack4j.entity.OsClientV3;
import lombok.RequiredArgsConstructor;
import org.openstack4j.api.Builders;
import org.openstack4j.model.network.NetFloatingIP;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.model.network.options.PortListOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NetworkService {

    private static final Logger log = LoggerFactory.getLogger(NetworkService.class);
    private final OsClientV3 osClientV3;

    /**
     * @param : X
     * @return : networks
     * @description : 존재하는 네트워크들의 정보를 반환합니다.
     **/
    public List<? extends Network> getNetworks() {
        return osClientV3.getOS().networking().network().list();
    }


    /**
     * @param :networkId
     * @return : network
     * @description : 네트워크 ID를 받아 해당 네트워크 정보를 반환합니다.
     **/
    public Network getNetwork(String networkId) {
        return osClientV3.getOS().networking().network().get(networkId);
    }


    /**
     * @param : X
     * @return : subnets
     * @description : 존재하는 서브넷들의 정보를 반환합니다.
     **/
    public List<? extends Subnet> getSubnets() {
        return osClientV3.getOS().networking().subnet().list();
    }

    /**
     * @param : subnetId
     * @return : subnet
     * @description : 서브넷 ID를 받아 해당 서브넷의 정보를 반환합니다.
     **/
    public Subnet getSubnet(String subnetId) {
        return osClientV3.getOS().networking().subnet().get(subnetId);
    }

    /**
     * @param : serverId, private-network-id
     * @return : port
     * @description : 서버 ID와 프라이빗 네트워크의 ID를 받아 서버의 포트를 반환합니다.
     **/
    public Port getServerPort(NetworkDto networkDto) {
        return osClientV3.getOS().networking().port().list(
            PortListOptions.create().deviceId(networkDto.getServerId())
                .networkId(networkDto.getNetworkId())
        ).get(0);
    }

    /**
     * @param : portId, public-network-id
     * @return : NetFloatingIP
     * @description : 포트 번호와 퍼블릿 네트워크 ID를 받아 유동 IP를 생성 인스턴스와 연결합니다.
     **/
    public NetFloatingIP createFloatingIP(NetworkDto networkDto) {
        // Create floating IP in the public network
        NetFloatingIP fip = Builders.netFloatingIP().portId(networkDto.getPortId())
            .floatingNetworkId(networkDto.getFloatingNetworkId()).build();
        return osClientV3.getOS().networking().floatingip().create(fip);
    }

}
