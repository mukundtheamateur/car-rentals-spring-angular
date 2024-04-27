package com.cts.thundercars.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
 
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticatorfilter;
 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	    	.cors(cors -> cors.configurationSource(request -> {
		        CorsConfiguration configuration = new CorsConfiguration();
		        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));  // Set allowed origins to localhost:4200
		        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
		        configuration.setAllowedHeaders(Arrays.asList("Content-Type","application/json", "Authorization","jwt"));
		        configuration.setExposedHeaders(Arrays.asList("jwt"));
		        configuration.setAllowCredentials(true);
		        return configuration;
		    }))
	    
	        .csrf(csrf->csrf.disable())
	        .authorizeHttpRequests(httpRequest-> httpRequest.requestMatchers("/swagger-ui/**").permitAll()
	        .requestMatchers("/api/register").permitAll()
	        .requestMatchers("/api/register/carDealer").permitAll()
	        .requestMatchers("/api/login").permitAll()
	        .requestMatchers("/api/login/load/user").permitAll()
	        .requestMatchers("/api/users/create").permitAll()
	        .requestMatchers("/api/login/carDealer").permitAll()
	        .requestMatchers("/api/bookings/**").permitAll()
	        .requestMatchers("/api/users/email/**").permitAll()
	        .requestMatchers("/api/users/**").permitAll()
	        .requestMatchers("/api/address/**").permitAll()
	        .requestMatchers("/api/cars/**").permitAll()
	        .requestMatchers("/api/cars/dealer**").permitAll()
	        .requestMatchers("/api/cardealer/**").permitAll()
	        .requestMatchers("api/roles/**").permitAll()
	        .anyRequest().authenticated())
	        .addFilterBefore(jwtAuthenticatorfilter, UsernamePasswordAuthenticationFilter.class);
	   
	    return http.build();
	}
 
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	}
}
