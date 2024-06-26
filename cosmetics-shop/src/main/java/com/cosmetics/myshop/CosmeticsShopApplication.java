package com.cosmetics.myshop;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cosmetics.myshop.model.Provider;
import com.cosmetics.myshop.model.Role;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.repository.RoleRepository;
import com.cosmetics.myshop.repository.UserRepository;

@SpringBootApplication
public class CosmeticsShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosmeticsShopApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Role adminRole;
			Role userRole;
			if (roleRepository.findByAuthority("ROLE_ADMIN").isEmpty()) {
	            adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
	            System.out.println(adminRole);
	        }

	        if (roleRepository.findByAuthority("ROLE_USER").isEmpty()) {
	            userRole = roleRepository.save(new Role("ROLE_USER"));
	            System.out.println(userRole);
	        }

	        if (userRepository.findByUsername("admin").isEmpty()) {
	            Set<Role> roles = new HashSet<>();
	            roles.add(roleRepository.findByAuthority("ROLE_ADMIN").orElseThrow());
	            User admin = new User("admin", passwordEncoder.encode("123"),"Admin", "","","","/img/user/no_avatar.png","","LOCAL",roles);
	            userRepository.save(admin);
	        }
	        if (userRepository.findByUsername("user1").isEmpty()) {
	        	Set<Role> roles = new HashSet<>();
	        	roles.add(roleRepository.findByAuthority("ROLE_USER").orElseThrow());
	            User user = new User("user1", passwordEncoder.encode("123"),"User", "","","","/img/user/no_avatar.png","","LOCAL",roles);
	        	userRepository.save(user);
	        }

		};
	}

}
