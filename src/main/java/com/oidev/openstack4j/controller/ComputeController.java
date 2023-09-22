package com.oidev.openstack4j.controller;

import com.oidev.openstack4j.entity.FlavorDto;
import com.oidev.openstack4j.entity.InstanceDto;
import com.oidev.openstack4j.service.ComputeService;
import lombok.RequiredArgsConstructor;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.InstanceAction;
import org.openstack4j.model.compute.Server;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ComputeController {

    private final ComputeService computeService;

    @GetMapping(value = "/nova/instances")
    public ResponseEntity<List<? extends Server>> getInstances(
        @RequestParam(required = false) String status) {
        if (status != null) {
            return new ResponseEntity<>(computeService.getInstancesByStatus(status), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(computeService.getInstances(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/nova/instances/{serverId}")
    public ResponseEntity<Server> getInstance(@PathVariable String serverId) {
        return new ResponseEntity<>(computeService.getInstance(serverId), HttpStatus.OK);
    }

    @PostMapping(value = "/nova/instances")
    public ResponseEntity<Server> createInstance(@RequestBody InstanceDto instanceDto) {
        return new ResponseEntity<>(computeService.createInstance(instanceDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/nova/instances/{serverId}")
    public ResponseEntity<ActionResponse> deleteInstance(@PathVariable String serverId) {
        return new ResponseEntity<>(computeService.deleteInstance(serverId), HttpStatus.OK);
    }

    @PutMapping(value = "/nova/instances/{serverId}")
    public ResponseEntity<ActionResponse> changeStatusInstance(@PathVariable String serverId,
        @RequestBody InstanceAction instanceAction) {
        return new ResponseEntity<>(
            computeService.changeStatusInstance(serverId, instanceAction.getAction()),
            HttpStatus.OK);
    }

    @PostMapping(value = "/nova/instances/flavor")
    public ResponseEntity<Flavor> createFlavor(@RequestBody FlavorDto flavorDto) {
        return new ResponseEntity<>(computeService.createFlavor(flavorDto), HttpStatus.OK);
    }

    @GetMapping(value = "/nova/instances/flavor")
    public ResponseEntity<List<? extends Flavor>> getFlavors() {
        return new ResponseEntity<>(computeService.getFlavors(), HttpStatus.OK);
    }
}
