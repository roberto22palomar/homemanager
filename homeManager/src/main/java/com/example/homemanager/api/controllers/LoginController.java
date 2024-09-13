package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.LoginRequest;
import com.example.homemanager.api.models.responses.UserResponse;
import com.example.homemanager.infraestructure.abstract_services.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "login")
@AllArgsConstructor

public class LoginController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> post(@Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(userService.checkLogin(request));
    }


}
