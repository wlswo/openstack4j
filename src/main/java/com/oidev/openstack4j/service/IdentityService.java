package com.oidev.openstack4j.service;


import com.oidev.openstack4j.entity.OsClientV3;
import lombok.RequiredArgsConstructor;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.identity.v3.Domain;
import org.openstack4j.model.identity.v3.Project;
import org.openstack4j.model.identity.v3.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class IdentityService {

    private final Logger log = LoggerFactory.getLogger(IdentityService.class);
    private final OsClientV3 osClientV3;

    /**
     * @param : tokenId
     * @return : Token
     * @description : tokenId에 해당하는 Token을 반환합니다.
     **/
    public Token getToken(String tokenId) {
        return osClientV3.getOS().identity().tokens().get(tokenId);
    }

    /**
     * @param : tokenId
     * @return : ActionResponse
     * @description : tokenId를 받아 해당 토큰의 유효성을 검증합니다. 검증 정보를 반환합니다.
     **/
    public ActionResponse validateToken(String tokenId) {
        return osClientV3.getOS().identity().tokens().check(tokenId);
    }

    /**
     * @param : tokenId
     * @return : Services
     * @description : tokenId를 받아 해당 Id를 가지는 토큰이 사용 가능한 서비스 리스트를 반환합니다.
     **/
    public List<? extends org.openstack4j.model.identity.v3.Service> getTokenServiceScope(
            String tokenId) {
        return osClientV3.getOS().identity().tokens().getServiceCatalog(tokenId);
    }

    /**
     * @param : tokenId
     * @return : projects
     * @description : tokenId를 받아 해당 Id를 가지는 토큰이 사용 가능한 프로젝트 리스트를 반환합니다.
     **/
    public List<? extends Project> getTokenProjectScope(String tokenId) {
        return osClientV3.getOS().identity().tokens().getProjectScopes(tokenId);
    }

    /**
     * @param : tokenId
     * @return : domains
     * @description : tokenId를 받아 해당 Id를 가지는 토큰이 사용 가능한 도메인 리스트를 반환합니다.
     **/
    public List<? extends Domain> getTokenDomainScope(String tokenId) {
        return osClientV3.getOS().identity().tokens().getDomainScopes(tokenId);
    }

    /**
     * @param : tokenId
     * @return : ActionResponse
     * @description : tokenId를 받아 해당 토큰을 만료시킵니다. 작업의 상태를 반환합니다.
     **/
    public ActionResponse deleteToken(String tokenId) {
        return osClientV3.getOS().identity().tokens().delete(tokenId);
    }
}
