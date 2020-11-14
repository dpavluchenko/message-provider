package com.pavliuchenko.messageprovider.domain.security;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class JwtInfo {

    private String subject;
    private Map<String, Object> claims;

    public static final String ROLE_KEY = "role";

    public static JwtInfo fromUser(UserInfo user) {
        return JwtInfo.builder()
                .claims(Map.of(ROLE_KEY, user.getRole().name()))
                .subject(user.getId().toString())
                .build();
    }

    public UserInfo toUser() {
        var user = new UserInfo();
        user.setRole(Role.valueOf(claims.get(ROLE_KEY).toString()));
        user.setId(Long.parseLong(subject));
        return user;
    }
}
