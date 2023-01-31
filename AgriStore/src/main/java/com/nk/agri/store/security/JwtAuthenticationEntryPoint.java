package com.nk.agri.store.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

// JWT STEP: 1 -> spring-starter-security -pom.xml
// JWT STEP: 2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint    //AuthEntry will be call when un authenticated exception is called
{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("Access Denied !! "+ authException.getMessage());
    }
}
