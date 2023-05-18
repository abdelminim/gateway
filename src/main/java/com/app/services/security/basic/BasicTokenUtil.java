package com.app.services.security.basic;

import java.io.Serializable;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class BasicTokenUtil implements Serializable {

    public String getUsernameFromToken(String token) {
        byte[] credDecoded = Base64.getDecoder().decode(token);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        int seperatorIndex = credentials.indexOf(':');
        return credentials.substring(0, seperatorIndex);
    }

    public String getPasswordFromToken(String token) {
        byte[] credDecoded = Base64.getDecoder().decode(token);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        int seperatorIndex = credentials.indexOf(':');
        return credentials.substring(seperatorIndex + 1);
    }

}
