package com.example.SpringBootProject.service;

import com.example.SpringBootProject.model.Post;
import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.PostRepository;
import com.example.SpringBootProject.repository.RoleRepository;
import com.example.SpringBootProject.repository.UserRepository;
import com.example.SpringBootProject.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PostRepository postRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) return user.get();
        else {
            throw new Error("User with id " + id + " doesn't exist.");
        }
    }

    public User getPostUser(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) return post.get().getUser();
        else {
            throw new Error("Post with ID " + postId + " doesn't exist!");
        }
    }

    public User getMe(Principal principal) {
        User user = User.getUser(principal, userRepository);
        return user;
    }

    public User deleteUser(Long userId) {
        Optional<User> deletedUser = userRepository.findById(userId);
        if (!deletedUser.isPresent()) {
            throw new Error("User with id " + userId + " doesn't exist.");
        } else {
            userRepository.delete(deletedUser.get());
            return deletedUser.get();
        }
    }

    public User deleteMe(Principal principal) {
        User user = User.getUser(principal, userRepository);
        userRepository.delete(user);
        return user;
    }

    public User updateMe(UserRequest userRequest, Principal principal) {
        User user = User.getUser(principal, userRepository);
        user.setName(userRequest.getName() == null ? user.getName() : userRequest.getName());
        user.setUsername(userRequest.getName() == null ? user.getUsername() : userRequest.getUsername());
        user.setPassword(userRequest.getPassword() == null ? user.getPassword() : passwordEncoder.encode(userRequest.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) throw new Error("User with id " + userId + " doesn't exist!");
        else {
            user.get().setName(userRequest.getName() == null ? user.get().getName() : userRequest.getName());
            user.get().setUsername(userRequest.getName() == null ? user.get().getUsername() : userRequest.getUsername());
            user.get().setPassword(userRequest.getPassword() == null ? user.get().getPassword() : passwordEncoder.encode(userRequest.getPassword()));
            return userRepository.save(user.get());
        }
    }

    public User makeAdmin(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) throw new Error("User with id " + userId + " doesn't exist.");
        else {
            if (user.get().isAdmin()) throw new Error("User with id " + userId + " is already admin.");
            else {
                Role adminRole = roleRepository.getRoleByName("ROLE_ADMIN");
                user.get().addRole(adminRole);
                return userRepository.save(user.get());
            }
        }
    }

    public User removeAdmin(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) throw new Error("User with id " + userId + " doesn't exist.");
        else {
            if (!user.get().isAdmin()) throw new Error("User with id " + userId + " is not admin.");
            else {
                Role adminRole = roleRepository.getRoleByName("ROLE_ADMIN");
                user.get().removeRole(adminRole);
                return userRepository.save(user.get());
            }
        }
    }


}
