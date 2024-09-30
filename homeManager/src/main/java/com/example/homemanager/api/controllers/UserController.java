package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.responses.UserResponse;
import com.example.homemanager.auth.models.requests.RegisterRequest;
import com.example.homemanager.infraestructure.abstract_services.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "user")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> post(@Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(userService.create(request));
    }

    @GetMapping
    public ResponseEntity<Void> checkLogged() {
        return ResponseEntity.noContent().build();
    }

}
