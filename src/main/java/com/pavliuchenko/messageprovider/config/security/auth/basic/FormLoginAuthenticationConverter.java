package com.pavliuchenko.messageprovider.config.security.auth.basic;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class FormLoginAuthenticationConverter extends ServerFormLoginAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return exchange
                .getFormData()
                .filter(data -> Objects.nonNull(data.getFirst("username")) && Objects.nonNull(data.getFirst("password")))
                .switchIfEmpty(Mono.empty())
                .flatMap(data-> super.convert(exchange));
    }
}
