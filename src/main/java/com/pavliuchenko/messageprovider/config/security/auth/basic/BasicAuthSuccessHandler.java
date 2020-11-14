package com.pavliuchenko.messageprovider.config.security.auth.basic;


import com.pavliuchenko.messageprovider.domain.security.UserInfo;
import com.pavliuchenko.messageprovider.service.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class BasicAuthSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return webFilterExchange.getChain()
                .filter(getExchangeWithAuthHeader(webFilterExchange.getExchange(), (UserInfo) authentication.getPrincipal()));
    }

    private ServerWebExchange getExchangeWithAuthHeader(ServerWebExchange exchange, UserInfo user) {
        exchange
                .getResponse()
                .getHeaders()
                .add(HttpHeaders.AUTHORIZATION,
                        String.join(" ", "Bearer", jwtService.createTokenForUser(user)));
        return exchange;
    }
}
