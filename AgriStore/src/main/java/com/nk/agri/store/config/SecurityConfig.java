package com.nk.agri.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    // IN MEMORY CONFIGURATION
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails admin  = User.builder()
//                .username("Nachiket")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        UserDetails normal = User.builder()
//                .username("Aniket")
//                .password(passwordEncoder().encode("admin"))
//                .roles("NORMAL")
//                .build();
//        //Users create
////        InMemoryUserDetailsManager - Is implementation class of UserDetailsService
//
//                return new InMemoryUserDetailsManager(admin,normal);
//    }

    //FROM DB


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        //Form base Login
//        http.authorizeRequests()
//                .anyRequest().authenticated().and()
//                .formLogin()
//                .loginPage("login.html")
//                .loginProcessingUrl("/process-url")
//                .defaultSuccessUrl("/dashboard")
//                .failureForwardUrl("/error")
//                .and()
//                .logout()
//                .logoutUrl("/do-logout");
//

        http.csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();


        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
