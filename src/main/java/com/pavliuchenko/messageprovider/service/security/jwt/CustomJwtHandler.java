package com.pavliuchenko.messageprovider.service.security.jwt;


import com.pavliuchenko.messageprovider.domain.security.JwtInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

public class CustomJwtHandler extends JwtHandlerAdapter<JwtInfo> {

    @Override
    public JwtInfo onClaimsJws(Jws<Claims> jws) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtInfo.ROLE_KEY, jws.getBody().get(JwtInfo.ROLE_KEY));
        return JwtInfo.builder()
                .claims(claims)
                .subject(jws.getBody().getSubject())
                .build();
    }
}
