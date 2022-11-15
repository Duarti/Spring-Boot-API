package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.model.Post;
import com.example.SpringBootProject.request.PostRequest;
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


    @GetMapping(path = "/api/posts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Post> getPosts(Principal principal){
        return postService.getPosts(principal);
    }

    @GetMapping(path = "/api/myposts")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public List<Post> getMyPosts(Principal principal) { return postService.getMyPosts(principal);}

    @GetMapping(path = "/api/post/{id}")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public Post getPost(@PathVariable Long id, Principal principal){
        return postService.getPost(principal, id);
    }

    @GetMapping(path = "/api/user/{userId}/posts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Post> getUserPosts(@PathVariable Long userId){
        return postService.getUserPosts(userId);
    }

    @PostMapping(path="/api/posts")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public Post createPost(@RequestBody PostRequest postRequest, Principal principal) {
        return postService.createPost(postRequest, principal);
    }

    @DeleteMapping(path="/api/post/{postId}")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public Post deletePost(@PathVariable Long postId, Principal principal) {
        return postService.deletePost(postId, principal);
    }

    @PutMapping(path="api/post/{postId}")
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public Post updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest, Principal principal){
        return postService.updatePost(postId, postRequest, principal);
    }

}
