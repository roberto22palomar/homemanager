package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.UserRequest;
import com.example.homemanager.api.models.responses.UserResponse;
import com.example.homemanager.config.CustomUserDetails;
import com.example.homemanager.infraestructure.abstract_services.IUserService;
import com.example.homemanager.utils.exceptions.UserCredentialsException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "user")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> post(@Valid @RequestBody UserRequest request) {

        return ResponseEntity.ok(userService.create(request));
    }

    @GetMapping("/current-user")
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return UserResponse.builder()
                    .username(userDetails.getUsername())
                    .id(userDetails.getId())
                    .email(userDetails.getEmail())
                    .casas(userDetails.getCasas())
                    .build();
        } else {
            throw new UserCredentialsException("No authenticated user found");
        }
    }

}
