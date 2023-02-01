package com.nk.agri.store.config;

import com.nk.agri.store.security.JwtAuthenticationEntryPoint;
import com.nk.agri.store.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

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
                .requestMatchers("/auth/login")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/users/")
                .permitAll()
                .requestMatchers(HttpMethod.GET,"/users")
                .permitAll()
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);


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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

}
