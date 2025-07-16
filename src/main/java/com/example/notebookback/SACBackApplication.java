package com.example.notebookback;

import com.example.notebookback.models.ntities.ERole;
import com.example.notebookback.models.ntities.Role;
import com.example.notebookback.models.ntities.User;
import com.example.notebookback.repositories.RoleRepository;
import com.example.notebookback.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SACBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SACBackApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository,
						   PasswordEncoder encoder) {
		return args -> {
			if(userRepository.findAll().isEmpty()){
				roleRepository.deleteAll();
				roleRepository.save(new Role(ERole.ROLE_ADMIN));
				roleRepository.save(new Role(ERole.ROLE_STUDENT));
				roleRepository.save(new Role(ERole.ROLE_TEACHER));
				User user = new User();
				user.setUsername("admin");
				user.setPassword(encoder.encode("admin"));
				Set<Role> roles = new HashSet<>();
				roles.add(roleRepository.findByName(ERole.valueOf("ROLE_ADMIN")).get());
				roles.add(roleRepository.findByName(ERole.valueOf("ROLE_STUDENT")).get());
				roles.add(roleRepository.findByName(ERole.valueOf("ROLE_TEACHER")).get());
				user.setRoles(roles);
				userRepository.save(user);
			}

		};
	}
}
