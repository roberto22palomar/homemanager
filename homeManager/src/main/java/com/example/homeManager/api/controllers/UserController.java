package com.example.homeManager.api.controllers;

import com.example.homeManager.api.models.request.UserRequest;
import com.example.homeManager.api.models.responses.UserResponse;
import com.example.homeManager.infraestructure.abstract_services.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> post(@Valid @RequestBody UserRequest request) {

        return ResponseEntity.ok(userService.create(request));
    }

}
