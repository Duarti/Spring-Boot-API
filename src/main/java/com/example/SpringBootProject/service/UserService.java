package com.example.SpringBootProject.service;

import com.example.SpringBootProject.model.Post;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.PostRepository;
import com.example.SpringBootProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PostRepository postRepository;
    @Autowired
    UserService(UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) return user.get();
        else {
            throw new Error("User with id " + id + " doesn't exist.");
        }
    }

    public User getPostUser(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()) return post.get().getUser();
        else {
            throw new Error("Post with ID " + postId + " doesn't exist!");
        }
    }



}
