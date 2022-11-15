package com.example.SpringBootProject;

import com.example.SpringBootProject.model.Post;
import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.PostRepository;
import com.example.SpringBootProject.repository.RoleRepository;
import com.example.SpringBootProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
//@SpringBootApplication
public class SpringBootProjectApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private RoleRepository roleRepository;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Role user = new Role("ROLE_USER");
		Role admin = new Role("ROLE_ADMIN");

		user = roleRepository.save(user);
		admin = roleRepository.save(admin);

		User user1 = new User("Duart Nishefci", "duartnishefci", passwordEncoder.encode("kompjuteri13"));
		User user2 = new User("Leart Nishefci", "leartnishefci", passwordEncoder.encode("kompjuteri13"));
		User user3 = new User("Elton Nishefci", "eltonnishefci", passwordEncoder.encode("kompjuteri13"));

		user1.addRole(admin);
		user2.addRole(user);
		user3.addRole(user);

		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);
		user3 = userRepository.save(user3);

		Post post1 = new Post("PostTitle1", "PostBody", user1);

		postRepository.save(post1);
		postRepository.save(new Post("PostTitle2", "PostBody", user2));
	}
}
