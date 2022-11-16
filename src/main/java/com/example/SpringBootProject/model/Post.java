package com.example.SpringBootProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(generator = "post_id_generator")
    @GenericGenerator(
            name = "post_id_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator"
    )
    private Long id;
    @Column(length = 20)
    private String title;
    @Column(length = 50)
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
