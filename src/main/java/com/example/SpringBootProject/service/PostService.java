package com.example.SpringBootProject.service;

import com.example.SpringBootProject.model.Post;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.PostRepository;
import com.example.SpringBootProject.repository.UserRepository;
import com.example.SpringBootProject.request.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Principal principal, Long id) {
        User user = User.getUser(principal, userRepository);
        System.out.println(user.getRoles());
        if (!user.isAdmin()) {
            Optional<Post> post = postRepository.findById(id);
            if (!post.isPresent()) throw new Error("Post with id " + id + " doesn't exist!");
            else {
                if (user.getId() == post.get().getUser().getId()) {
                    return post.get();
                } else {
                    throw new Error("You don't have access to this post.");
                }
            }
        } else {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) return post.get();
            else {
                throw new Error("Post with id " + id + " doesn't exist.");
            }
        }
    }

    public List<Post> getUserPosts(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) return postRepository.findByUser(user.get());
        else {
            throw new Error("User with ID " + userId + " doesn't exist.");
        }
    }

    public List<Post> getMyPosts(Principal principal) {
        User user = User.getUser(principal, userRepository);
        return user.getPosts();
    }

    public Post createPost(PostRequest postRequest, Principal principal) {
        User user = User.getUser(principal, userRepository);
        Post post = new Post(postRequest.getTitle(), postRequest.getBody(), user);
        return postRepository.save(post);
    }

    public Post deletePost(Long postId, Principal principal) {
        User user = User.getUser(principal, userRepository);
        Optional<Post> post = postRepository.findById(postId);
        if (!post.isPresent()) throw new Error("Post with id " + postId + "doesn't exist!");
        else {
            if (user.isAdmin()) {
                postRepository.delete(post.get());
                return post.get();
            } else {
                if (user.getId() != post.get().getUser().getId()) {
                    throw new Error("You can't delete this post!");
                } else {
                    postRepository.delete(post.get());
                    return post.get();
                }
            }
        }
    }


    public Post updatePost(Long postId, PostRequest postRequest, Principal principal) {
        User user = User.getUser(principal, userRepository);
        Optional<Post> post = postRepository.findById(postId);
        if (!post.isPresent()) throw new Error("Post with id " + postId + "doesn't exist!");
        else {
            if (user.isAdmin()) {
                Post updatedPost = post.get();
                updatedPost.setBody(postRequest.getBody());
                updatedPost.setTitle(postRequest.getTitle());
                return postRepository.save(updatedPost);
            } else {
                if (user.getId() != post.get().getUser().getId()) {
                    throw new Error("You can't update this post!");
                } else {
                    Post updatedPost = post.get();
                    updatedPost.setBody(postRequest.getBody());
                    updatedPost.setTitle(postRequest.getTitle());
                    return postRepository.save(updatedPost);
                }
            }
        }
    }


}
