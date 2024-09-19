package com.example.devicesservice.security;

import com.example.devicesservice.contexts.AuthCertificate;
import com.example.devicesservice.contexts.SecurityContext;
import com.example.devicesservice.dtos.ErrorResponse;
import com.example.devicesservice.exceptions.BaseException;
import com.example.devicesservice.mappers.ErrorMapper;
import com.example.devicesservice.services.AuthService;
import com.example.devicesservice.utils.JwtTokenUtil;
import com.example.devicesservice.utils.TokenPayload;
import com.example.devicesservice.utils.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final AuthService authService;

    private final JwtTokenUtil jwtTokenUtil;

    private final ErrorMapper errorMapper;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        try {
            if (authorization != null) {
                TokenPayload payload = jwtTokenUtil.validateToken(authorization, TokenType.ACCESS_TOKEN);

                AuthCertificate authCertificate = authService.getAuthCertificateByUserId(payload.getUserId());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        authCertificate.getUser(), null, List.of()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                SecurityContext.setAuthenticationCertificate(authCertificate);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleUnauthorizedException(response, e);
        } finally {
            SecurityContext.clearAuthenticationCertificate();
        }
    }

    private void handleUnauthorizedException(HttpServletResponse response, Exception ex) throws IOException {
        response.setContentType("application/json");

        if(ex instanceof BaseException e) {
            ErrorResponse errorResponse = errorMapper.toErrorResponse(e);

            response.setStatus(e.getStatus());
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }

        ex.printStackTrace();

        response.setStatus(500);
        response.getWriter().write("Internal server error");
    }

}
