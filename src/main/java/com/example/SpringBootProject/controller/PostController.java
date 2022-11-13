package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.model.Post;
import com.example.SpringBootProject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class PostController {

    private PostService postService;
    @Autowired
    PostController(PostService postService){
        this.postService = postService;
    }


    @GetMapping(path = "/posts")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_USER')")
    public List<Post> getPosts(Principal principal){
        return postService.getPosts(principal);
    }

    @GetMapping(path = "/post/{id}")
    public Post getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @GetMapping(path = "/user/{userId}/posts")
    public List<Post> getUserPosts(@PathVariable Long userId){
        return postService.getUserPosts(userId);
    }

//    @PostMapping(path="/posts")
//    public Post addPost(@RequestBody Post post){
//
//    }

}
