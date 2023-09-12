package com.oidev.openstack4j.openstackServiceTest;

import org.junit.jupiter.api.*;
import org.openstack4j.api.OSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageServiceTest {

    @Autowired
    private TestConfig testConfig;
    public OSClient.OSClientV3 os;

}
