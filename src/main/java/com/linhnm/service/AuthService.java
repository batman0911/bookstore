package com.linhnm.service;

import com.linhnm.common.response.ErrorCode;
import com.linhnm.exception.CommonException;
import com.linhnm.model.dto.Login;
import com.linhnm.repository.UserRepository;
import com.linhnm.security.JwtService;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public Login.Response generateToken(Login.Request authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
            if (authentication.isAuthenticated()) {
                List<String> roles = userRepository.getUserRoles(authRequest.username());
                String token = jwtService.generateToken(authRequest.username(), roles);
                Date expirationDate = jwtService.extractExpiration(token);
                Long expireAt = expirationDate.getTime() / 1000;
                return new Login.Response(token, token, expireAt);
            } else {
                throw new UsernameNotFoundException("invalid user request");
            }
        } catch (RuntimeException e) {
            throw new CommonException(ErrorCode.INVALID_CREDENTIALS);
        }
    }
}
