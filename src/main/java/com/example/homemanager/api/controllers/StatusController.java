package com.example.homemanager.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "status")
@AllArgsConstructor
@Slf4j
@Tag(name = "Status requests", description = "Endpoints related to the server status")
public class StatusController {

    @Operation(summary = "Estado del servidor.", description = "Petición para comprobar el estado del servidor.")
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.info("Server health: OK");
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(status);
    }
}
