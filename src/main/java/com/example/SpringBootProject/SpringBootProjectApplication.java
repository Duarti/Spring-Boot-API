package com.example.SpringBootProject;

import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class SpringBootProjectApplication implements CommandLineRunner {
    private RoleRepository roleRepository;

    public SpringBootProjectApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");
        if (!roleRepository.existsById(1L)) roleRepository.save(user);
        if (!roleRepository.existsById(2L)) roleRepository.save(admin);
    }
}
