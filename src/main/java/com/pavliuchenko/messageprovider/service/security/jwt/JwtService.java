package com.pavliuchenko.messageprovider.service.security.jwt;


import com.pavliuchenko.messageprovider.domain.security.JwtInfo;
import com.pavliuchenko.messageprovider.domain.security.UserInfo;
import reactor.core.publisher.Mono;

public interface JwtService {
    Mono<JwtInfo> validateToken(String token);
    String createTokenForUser(UserInfo user);
}
