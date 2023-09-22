package com.oidev.openstack4j.controller;

import com.oidev.openstack4j.entity.ImageDto;
import com.oidev.openstack4j.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.image.v2.Image;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/glance/images")
    public ResponseEntity<Image> createImage(@RequestBody ImageDto imageDto)
        throws MalformedURLException {
        return new ResponseEntity<>(imageService.createImage(imageDto), HttpStatus.OK);
    }

    @GetMapping(value = "/glance/images")
    public ResponseEntity<List<? extends Image>> getImages() {
        return new ResponseEntity<>(imageService.getImageList(), HttpStatus.OK);
    }

    @GetMapping(value = "/glance/images/{imageId}")
    public ResponseEntity<Image> getImage(@PathVariable String imageId) {
        return new ResponseEntity<>(imageService.getImage(imageId), HttpStatus.OK);
    }

    @PutMapping(value = "/glance/images/{imageId}/activate")
    public ResponseEntity<ActionResponse> activeImage(@PathVariable String imageId) {
        return new ResponseEntity<>(imageService.activateImage(imageId), HttpStatus.OK);
    }

    @PutMapping(value = "/glance/images/{imageId}/deactivate")
    public ResponseEntity<ActionResponse> deactivateImage(@PathVariable String imageId) {
        return new ResponseEntity<>(imageService.deactivateImage(imageId), HttpStatus.OK);
    }
}
