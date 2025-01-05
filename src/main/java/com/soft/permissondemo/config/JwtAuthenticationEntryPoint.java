package com.soft.permissondemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soft.permissondemo.common.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        String message = "访问此资源需要完全身份验证";
        if (authException != null) {
            message = authException.getMessage();
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(Result.error(401, message));
        
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
    }
} 