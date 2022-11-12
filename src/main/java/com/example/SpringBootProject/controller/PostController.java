package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.model.Post;
import com.example.SpringBootProject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private PostService postService;
    @Autowired
    PostController(PostService postService){
        this.postService = postService;
    }


    @GetMapping(path = "/posts")
    @PreAuthorize("hasRole('Admin')")
    public List<Post> getPosts(){
        return postService.getPosts();
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
