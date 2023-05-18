package com.app.services.security.service;

import com.app.services.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.services.security.basic.BasicUserFactory;
import com.app.services.security.repository.UserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else if (!user.getEnabled()) {
            throw new UsernameNotFoundException(String.format("User is disabled '%s'.", username));
        } else {
            return BasicUserFactory.create(user);
        }
    }
}
