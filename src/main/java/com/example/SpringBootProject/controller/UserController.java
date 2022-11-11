package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }



    @GetMapping(path = "/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(path = "/user/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @GetMapping(path = "/post/{postId}/user")
    public User getPostUser(@PathVariable Long postId){
        return userService.getPostUser(postId);
    }

}
