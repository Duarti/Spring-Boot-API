package com.example.SpringBootProject.model;


import lombok.*;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    public Role(String name){
        this.name = name;
    }



}