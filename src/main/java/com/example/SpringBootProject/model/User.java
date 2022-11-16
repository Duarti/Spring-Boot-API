package com.example.SpringBootProject.model;

import com.example.SpringBootProject.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "user_id_generator")
    @GenericGenerator(
            name = "user_id_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator"
    )
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(unique = true, length = 20)
    private String username;
    @Column(length = 20)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts;

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public boolean isAdmin() {
        boolean isAdmin = false;
        for (Role role : getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) isAdmin = true;
        }
        return isAdmin;
    }

    public static User getUser(Principal principal, UserRepository userRepository) {
        String username = principal.getName();
        User user = userRepository.getUserByUsername(username);
        return user;
    }
}
