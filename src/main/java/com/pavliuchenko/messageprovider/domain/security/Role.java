package com.pavliuchenko.messageprovider.domain.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN(0),
    USER(1);

    private int value;

    @Override
    public String getAuthority() {
        return name();
    }

    public static Role findByValue(Integer value) {
       return Arrays.stream(values()).filter(role -> role.getValue() == value).findFirst().orElse(null);
    }
}
