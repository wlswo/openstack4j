package com.oidev.openstack4j.controller;

import com.oidev.openstack4j.config.Config;
import lombok.RequiredArgsConstructor;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.compute.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class ComputeController {

    private final Config config;

    @GetMapping("/servers")
    public List getServerList() {
        OSClientV3 os = config.issueToken();

        List<? extends Server> servers = os.compute().servers().list();

        return servers;
    }

    @GetMapping("/server")
    public Server createServer() {
        OSClientV3 os = config.issueToken();

        Server server = os.compute().servers().get("eb98fdbf-f10b-429c-a1d7-b4f97e57b590");
        return server;
    }

}
