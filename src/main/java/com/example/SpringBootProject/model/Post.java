package com.example.SpringBootProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@Data @NoArgsConstructor @AllArgsConstructor
public class Post {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String body;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;


    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }
}
