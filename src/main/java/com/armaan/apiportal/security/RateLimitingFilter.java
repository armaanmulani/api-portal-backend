package com.armaan.apiportal.security;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimitingService rateLimitingService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/swagger-ui") || 
               path.startsWith("/v3/api-docs") || 
               path.startsWith("/webjars");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Check if an authenticated user principal exists in the context
        Object principal = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                : null;

        if (principal instanceof UserDetails userDetails) {
            Bucket bucket = rateLimitingService.resolveBucket(userDetails);

            if (!bucket.tryConsume(1)) {
                response.setStatus(429); // Too Many Requests status code
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Too Many Requests\", \"message\": \"You have exceeded your API quota. Please slow down.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
