package com.example.SpringBootProject.service;

import com.example.SpringBootProject.model.Post;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.PostRepository;
import com.example.SpringBootProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    public Post getPost(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()) return post.get();
        else {
            throw new Error("User with id " + id + " doesn't exist.");
        }
    }

    public List<Post> getUserPosts(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) return postRepository.findByUser(user.get());
        else {
            throw new Error("User with ID " + userId + " doesn't exist.");
        }
    }

}
