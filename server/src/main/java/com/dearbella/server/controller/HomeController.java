package com.dearbella.server.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public ResponseEntity<?> redirect() {
        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(URI.create("/swagger-ui/"));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    /*
    @GetMapping("/")
    public String redirect() {
        return "/swagger-ui/";
    }*/
}
