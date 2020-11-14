package com.pavliuchenko.messageprovider.config.security.auth.jwt;

import com.pavliuchenko.messageprovider.domain.security.JwtInfo;
import com.pavliuchenko.messageprovider.service.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class JwtToAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtService jwtService;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return jwtService
                .validateToken(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT isn't valid!"))))
                .map(JwtInfo::toUser)
                .map(user -> new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }
}
