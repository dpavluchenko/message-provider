package com.pavliuchenko.messageprovider.config.security;


import com.pavliuchenko.messageprovider.config.security.auth.basic.BasicAuthSuccessHandler;
import com.pavliuchenko.messageprovider.config.security.auth.basic.FormLoginAuthenticationConverter;
import com.pavliuchenko.messageprovider.config.security.auth.jwt.JwtReactiveAuthenticationManager;
import com.pavliuchenko.messageprovider.config.security.auth.jwt.JwtToAuthenticationConverter;
import com.pavliuchenko.messageprovider.service.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ReactiveUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/api/user/sign-up").permitAll()
                .and()
                .authorizeExchange()
                .pathMatchers("/api/user/sign-in")
                .authenticated()
                .and()
                .addFilterAt(createBasicFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange()
                .pathMatchers(getJwtPatterns())
                .authenticated()
                .and()
                .addFilterAt(createJwtAuthFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private WebFilter createBasicFilter() {
        var manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        manager.setPasswordEncoder(passwordEncoder());
        var filter = new AuthenticationWebFilter(manager);
        filter.setServerAuthenticationConverter(new FormLoginAuthenticationConverter());
        filter.setAuthenticationSuccessHandler(new BasicAuthSuccessHandler(jwtService));
        return filter;
    }

    private WebFilter createJwtAuthFilter() {
        var manager = new JwtReactiveAuthenticationManager();
        var filter = new AuthenticationWebFilter(manager);
        filter.setServerAuthenticationConverter(new JwtToAuthenticationConverter(jwtService));
        filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(getJwtPatterns()));
        return filter;
    }

    private String[] getJwtPatterns(){
        return new String[]{"/secure/**"};
    }

}