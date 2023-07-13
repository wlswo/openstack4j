package com.oidev.openstack4j.controller;

import com.oidev.openstack4j.config.Config;
import lombok.RequiredArgsConstructor;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.compute.Server;
import org.openstack4j.openstack.internal.OSClientSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ComputeController {

    private final Config config;

    @GetMapping("/servers")
    public List getServerList() {
        OSClientV3 os;
        if (OSClientSession.OSClientSessionV3.getCurrent() == null) {
            os = config.issueToken();
        }else {
            os = config.getOs();
        }

        List<? extends Server> servers = os.compute().servers().list();

        return servers;
    }

    @GetMapping("/servers/active")
    public List getActiveServerList() {
        OSClientV3 os;
        if (OSClientSession.OSClientSessionV3.getCurrent() == null) {
            os = config.issueToken();
        }else {
            os = config.getOs();
        }

        Map<String, String> filter = new HashMap<>();
        filter.put("status","PAUSED");
        List<? extends Server> servers = os.compute().servers().list(filter);

        return servers;
    }

    @PostMapping("/server")
    public ResponseEntity createServer() {
        return ResponseEntity.ok().build();
    }

}
