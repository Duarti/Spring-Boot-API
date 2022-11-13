package com.example.SpringBootProject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping
    public String home(Authentication authentication){
        return "Welcome Home21!";
    }

}
