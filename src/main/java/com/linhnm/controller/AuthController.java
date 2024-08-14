package com.linhnm.controller;

import com.linhnm.common.response.CommonResponse;
import com.linhnm.model.dto.Login;
import com.linhnm.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v${api.version}/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public CommonResponse<Login.Response> login(@Valid @RequestBody Login.Request loginRequest) {
        Login.Response res = authService.generateToken(loginRequest);
        return CommonResponse.of(res);
    }
}
