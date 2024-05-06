package com.example.demo.springboot.myfirstwebapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSedurityConfiguration {

	@Bean 	
	public InMemoryUserDetailsManager createUserDetailsManager() {
		
		
		UserDetails userDetails1 = createUserDetails("in28Minutes", "dummy");
		UserDetails userDetails2 = createUserDetails("ranga", "dummydummy");
		
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(userDetails1,userDetails2);
		
		return inMemoryUserDetailsManager;
	}

	private UserDetails createUserDetails(String username, String password) {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
		UserDetails userDetails = User.builder().passwordEncoder(passwordEncoder)
									.username(username)
									.password(password)
									.roles("USER", "ADMIN")
									.build();
		return userDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		http.formLogin(withDefaults());
		http.csrf().disable();
		http.headers().frameOptions().disable();
		return http.build();
	}
}
