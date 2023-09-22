package com.oidev.openstack4j.controller;

import com.oidev.openstack4j.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.identity.v3.Domain;
import org.openstack4j.model.identity.v3.Project;
import org.openstack4j.model.identity.v3.Service;
import org.openstack4j.model.identity.v3.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class IdentityController {

    private final IdentityService identityService;

    @GetMapping(value = "/keystone/tokens/{userTokenId}")
    public ResponseEntity<Token> getToken(@PathVariable String userTokenId) {
        return new ResponseEntity<>(identityService.getToken(userTokenId), HttpStatus.OK);
    }

    @GetMapping(value = "/keystone/tokens/{userTokenId}/validate")
    public ResponseEntity<ActionResponse> getValidateToken(@PathVariable String userTokenId) {
        return new ResponseEntity<>(identityService.validateToken(userTokenId), HttpStatus.OK);
    }

    @GetMapping(value = "/keystone/tokens/{userTokenId}/catalog")
    public ResponseEntity<List<? extends Service>> getTokenServiceScope(
            @PathVariable String userTokenId) {
        return new ResponseEntity<>(identityService.getTokenServiceScope(userTokenId),
                HttpStatus.OK);
    }

    @GetMapping(value = "/keystone/tokens/{userTokenId}/project-scopes")
    public ResponseEntity<List<? extends Project>> getTokenProjectScope(
            @PathVariable String userTokenId) {
        return new ResponseEntity<>(identityService.getTokenProjectScope(userTokenId),
                HttpStatus.OK);
    }

    @GetMapping(value = "/keystone/tokens/{userTokenId}/domain-scopes")
    public ResponseEntity<List<? extends Domain>> getTokenDomainScope(
            @PathVariable String userTokenId) {
        return new ResponseEntity<>(identityService.getTokenDomainScope(userTokenId),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/keystone/tokens/{userTokenId}")
    public ResponseEntity<ActionResponse> deleteToken(@PathVariable String userTokenId) {
        return new ResponseEntity<>(identityService.deleteToken(userTokenId), HttpStatus.OK);
    }
}
