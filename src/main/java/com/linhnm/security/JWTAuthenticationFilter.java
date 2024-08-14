package com.linhnm.security;

import com.linhnm.common.Context;
import com.linhnm.common.FilterResponseHandler;
import com.linhnm.common.response.ErrorCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        Context context = new Context();
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            FilterResponseHandler.returnError(response, ErrorCode.INVALID_ACCESS_TOKEN, "invalid access token");
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            boolean isTokenValid = jwtService.validateToken(jwt, userDetails);
            if (isTokenValid) {
                Claims claims = jwtService.extractAllClaims(jwt);
                List<String> roles = claims.get("rol", List.class);
                List<GrantedAuthority> auths = new ArrayList<>();
                roles.forEach(role -> auths.add(new SimpleGrantedAuthority(role)));
                JWTAuthentication jwtAuthentication = new JWTAuthentication();
                jwtAuthentication.setAuthenticated(true);
                jwtAuthentication.setAuthorities(auths);
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);

                context.setUser(userDetails);
                request.setAttribute("ctx", context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
