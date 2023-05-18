package com.app.services.security.basic;

import com.app.services.model.AuthService;
import com.app.services.model.AuthUser;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

public final class BasicUserFactory {

    private BasicUserFactory() {
    }

    public static BasicUser create(AuthUser user) {
        return new BasicUser(
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthServiceList()),
                user.getRole(),
                user.getEnabled()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<AuthService> authServices) {
        return authServices.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getPath()))
                .collect(Collectors.toList());
    }
}
