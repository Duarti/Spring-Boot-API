package com.example.SpringBootProject.security;

import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.UserRepository;
import com.example.SpringBootProject.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RegisterController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private SecurityService securityService;
    @Autowired
    public RegisterController(UserRepository userRepository, SecurityService securityService){
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.securityService = securityService;
    }

    @PostMapping(path = "/api/register")
    public User register(@RequestBody UserRequest userRequest){
        return securityService.register(userRequest);
    }


    @PostMapping(path="/api/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        securityService.refreshToken(request, response);
    }


}
