package com.app.services.security.repository;

import com.app.services.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 * Created by stephan on 20.03.16.
 */
@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findByUsername(String username);
}
