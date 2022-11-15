package com.example.SpringBootProject.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.SpringBootProject.model.Post;
import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.PostRepository;
import com.example.SpringBootProject.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

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

    public User getMe(Principal principal){
        User user = User.getUser(principal, userRepository);
        return user;
    }



}
