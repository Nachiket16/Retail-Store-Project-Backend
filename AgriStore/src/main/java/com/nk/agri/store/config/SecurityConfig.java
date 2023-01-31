package com.nk.agri.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig
{

    // IN MEMORY CONFIGURATION
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin  = User.builder()
                .username("Nachiket")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails normal = User.builder()
                .username("Aniket")
                .password(passwordEncoder().encode("admin"))
                .roles("NORMAL")
                .build();
        //Users create
//        InMemoryUserDetailsManager - Is implementation class of UserDetailsService

                return new InMemoryUserDetailsManager(admin,normal);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
