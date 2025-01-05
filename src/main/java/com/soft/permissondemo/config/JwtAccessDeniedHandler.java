package com.soft.permissondemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soft.permissondemo.common.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                      AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        String message = "权限不足，无法访问此资源";
        if (accessDeniedException != null) {
            message = accessDeniedException.getMessage();
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(Result.error(403, message));
        
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
    }
} 