package com.linhnm.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by linhnm on August 2024
 */

@RestController
public class HealthController {
    @GetMapping("/api/ping")
    @Operation(summary = "Ping server")
    public String ping() {
        return "pong";
    }
}
