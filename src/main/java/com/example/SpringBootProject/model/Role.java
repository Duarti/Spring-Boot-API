package com.example.SpringBootProject.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(generator = "role_id_generator")
    @GenericGenerator(
            name = "role_id_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator"
    )
    private Long id;

    @Column(length = 15)
    private String name;

    public Role(String name) {
        this.name = name;
    }

}