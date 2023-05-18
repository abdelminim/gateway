package com.app.services.security.basic;

import com.app.services.constants.GlobalConstant;
import com.app.services.model.AuthRole;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;

/**
 * Created by stephan on 20.03.16.
 */
public class BasicUser implements UserDetails {

    private final String username;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;
    private final AuthRole role;
    private final boolean enabled;

    public BasicUser(String username,String password,Collection<? extends GrantedAuthority> authorities,AuthRole role,boolean enabled) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.role = role;
        this.enabled = enabled;
    }

    public void removeAllAuthority() {
        authorities = new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public AuthRole getRole() {
        return role;
    }

    public boolean istServiceConsumer() {
        return role.getName().equals(GlobalConstant.serviceConsumerRole);
    }

}
