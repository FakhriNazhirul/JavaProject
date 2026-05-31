package com.project.organix.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        
        // Skip for API, assets, login, and registration
        if (uri.startsWith("/api") || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/login") || uri.startsWith("/register") || uri.startsWith("/error")) {
            return true;
        }

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null) {
            response.sendRedirect("/login");
            return false;
        }

        // Role-based access control
        if (uri.startsWith("/user/") && !"USER".equals(userRole)) {
            response.sendRedirect("/");
            return false;
        }

        if (!uri.startsWith("/user/") && !uri.equals("/logout") && "USER".equals(userRole)) {
            response.sendRedirect("/user/dashboard");
            return false;
        }

        return true;
    }
}
