package com.nk.agri.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig
{
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin  = User.builder()
                .username("Nachiket")
                .password("admin")
                .roles("ADMIN")
                .build();
        UserDetails normal = User.builder()
                .username("Aniket")
                .password("admin")
                .roles("NORMAL")
                .build();
        //Users create
//        InMemoryUserDetailsManager - Is implementation class of UserDetailsService

                return new InMemoryUserDetailsManager(admin,normal);
    }


}
