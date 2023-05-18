package com.app.services.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin
@RestController
@RequestMapping("/test/")
public class TestController {

    @PostMapping(value = "currentDate")
    public ResponseEntity<String> getDateTime() {
        return ResponseEntity.ok(new Date().toString());
    }

}
