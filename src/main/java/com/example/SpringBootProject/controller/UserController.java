package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.request.UserRequest;
import com.example.SpringBootProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;


@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //returns all users.
    @GetMapping(path = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    //returns user with id userId.
    @GetMapping(path = "/api/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    //returns current user.
    @GetMapping(path = "/api/me")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public User getMe(Principal principal) {
        return userService.getMe(principal);
    }

    //deletes current user.
    @DeleteMapping(path = "/api/me")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public User deleteMe(Principal principal) {
        return userService.deleteMe(principal);
    }

    //updates current user.
    @PutMapping(path = "/api/me")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public User updateMe(@RequestBody UserRequest userRequest, Principal principal) {
        return userService.updateMe(userRequest, principal);
    }

    //returns a post's with id postId user.
    @GetMapping(path = "/api/post/{postId}/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getPostUser(@PathVariable Long postId) {
        return userService.getPostUser(postId);
    }

    //deletes user with id userId.
    @DeleteMapping(path = "/api/user/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public User deleteUser(@PathVariable Long userId, Principal principal) {
        return userService.deleteUser(userId);
    }

    //updates user with id userId.
    @PutMapping(path = "/api/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest, Principal principal) {
        return userService.updateUser(userId, userRequest);
    }

    //gives admin role to user with id userId.
    @PutMapping(path = "/api/user/{userId}/makeadmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User makeAdmin(@PathVariable Long userId) {
        return userService.makeAdmin(userId);
    }

    //removes admin role to user with id userId.
    @PutMapping(path = "/api/user/{userId}/removeadmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User removeAdmin(@PathVariable Long userId) {
        return userService.removeAdmin(userId);
    }

}
