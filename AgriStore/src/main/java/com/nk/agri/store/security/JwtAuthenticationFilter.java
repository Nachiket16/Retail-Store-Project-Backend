package com.nk.agri.store.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT STEP: 4
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Authorization

        String requestHeader = request.getHeader("Authorization");
        log.info("Header : {}", requestHeader);
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            //Means token is present
            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                log.info("Illegal Argument exception");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
                log.info("JWT Expired !!! ");
            } catch (MalformedJwtException e) {
                e.printStackTrace();
                log.info("Some changes has done in token !!! Invalid Token");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Exception !!!! ");
            }
        } else {
            log.info("Invalid header value !!!!!!!!!!!! ");
        }

        //Username is present but no one is logged in yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //feth user detail from service
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {

                //Set the Authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                log.info("Validate Token Failed !!!!! ");
            }
        }
        filterChain.doFilter(request,response);

    }
}
