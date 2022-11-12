package com.example.SpringBootProject.security;

import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.UserRepository;
import com.example.SpringBootProject.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public RegisterController(UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping(path = "/register")
    public User register(@RequestBody UserRequest userRequest){
        User user = userRepository.getUserByUsername(userRequest.getUsername());
        if( user != null) throw new Error("User with such username already exists!");
        System.out.println(userRequest);
        user = new User();
        user.setUsername(userRequest.getUsername());
        user.addRole(new Role("User"));
        user.setName(userRequest.getName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        System.out.println(user);
        userRepository.save(user);
        return user;
    }


}
