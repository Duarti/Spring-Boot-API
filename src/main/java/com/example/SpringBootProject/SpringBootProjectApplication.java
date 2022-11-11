package com.example.SpringBootProject;

import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}

	UserRepository userRepository;
    @Autowired
	CommandLineRunner commandLine(UserRepository userRepository){
		return args -> {
			userRepository.save(new User(null,"duart","duartn","helloworld",null));
			};
	};

}
