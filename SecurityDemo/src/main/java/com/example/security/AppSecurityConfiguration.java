package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppSecurityConfiguration {
    @Bean
    public UserDetailsManager userDetailsManager() {
        UserDetails aayu = User.builder()
                .username("aayu")
                .password("{noop}pass123")
                .roles("STUDENT") // Role will be stored as "ROLE_STUDENT"
                .build();
        UserDetails aryan = User.builder()
                .username("aryan")
                .password("{noop}pass123")
                .roles("TEACHER") // Role will be stored as "ROLE_TEACHER"
                .build();
        UserDetails abc = User.builder()
                .username("abc")
                .password("{noop}pass123")
                .roles("TEACHER", "STUDENT") // Role will be stored as "ROLE_TEACHER" and "ROLE_STUDENT"
                .build();

        return new InMemoryUserDetailsManager(aayu, aryan, abc);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/students")
                        .hasAnyRole("STUDENT", "TEACHER", "ADMIN") // Matches "ROLE_STUDENT", etc.
                        .requestMatchers(HttpMethod.GET, "/api/students/**")
                        .hasAnyRole("STUDENT", "TEACHER", "ADMIN") // Matches "ROLE_STUDENT", etc.
                        .requestMatchers(HttpMethod.POST, "/api/students")
                        .hasRole("TEACHER") // Matches "ROLE_TEACHER"
                        .requestMatchers(HttpMethod.PATCH, "/api/students/**")
                        .hasRole("TEACHER") // Matches "ROLE_TEACHER"
                        .requestMatchers(HttpMethod.DELETE, "/api/students/**")
                        .hasRole("ADMIN") // Matches "ROLE_ADMIN"
        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
