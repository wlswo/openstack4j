package com.oidev.openstack4j.controller;

import com.oidev.openstack4j.entity.NetworkDto;
import com.oidev.openstack4j.service.NetworkService;
import lombok.RequiredArgsConstructor;
import org.openstack4j.model.network.NetFloatingIP;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Subnet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NetworkController {

    private final NetworkService networkService;

    @GetMapping(value = "/neutron/networks")
    public ResponseEntity<List<? extends Network>> getNetworks() {
        return new ResponseEntity<>(networkService.getNetworks(), HttpStatus.OK);
    }

    @GetMapping(value = "/neutron/networks/{networkId}")
    public ResponseEntity<Network> getNetwork(@PathVariable String networkId) {
        return new ResponseEntity<>(networkService.getNetwork(networkId), HttpStatus.OK);
    }

    @GetMapping(value = "/neutron/networks/subnets")
    public ResponseEntity<List<? extends Subnet>> getSubnets() {
        return new ResponseEntity<>(networkService.getSubnets(), HttpStatus.OK);
    }

    @GetMapping(value = "/neutron/networks/subnets/{subnetId}")
    public ResponseEntity<Subnet> getSubnet(@PathVariable String subnetId) {
        return new ResponseEntity<>(networkService.getSubnet(subnetId), HttpStatus.OK);
    }

    @PostMapping(value = "/neutron/networks/ports")
    public ResponseEntity<Port> getServerPort(@RequestBody NetworkDto networkDto) {
        return new ResponseEntity<>(networkService.getServerPort(networkDto), HttpStatus.OK);
    }

    @PostMapping(value = "/neutron/networks/floating-ips")
    public ResponseEntity<NetFloatingIP> createFloatingIP(@RequestBody NetworkDto networkDto) {
        return new ResponseEntity<>(networkService.createFloatingIP(networkDto), HttpStatus.OK);
    }
}
