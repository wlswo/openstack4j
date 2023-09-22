package com.oidev.openstack4j.controller;

import com.oidev.openstack4j.service.VolumeService;
import lombok.RequiredArgsConstructor;
import org.openstack4j.model.storage.block.Volume;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VolumeController {

    private final VolumeService volumeService;

    @GetMapping(value = "/cinder/volumes")
    public ResponseEntity<List<? extends Volume>> getVolumes() {
        return new ResponseEntity<>(volumeService.getVolumes(), HttpStatus.OK);
    }

    @GetMapping(value = "/cinder/volumes/{volumeId}")
    public ResponseEntity<Volume> getVolume(@PathVariable String volumeId) {
        return new ResponseEntity<>(volumeService.getVolume(volumeId), HttpStatus.OK);
    }

}
