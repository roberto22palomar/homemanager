package com.example.homemanager.auth.services;

import com.example.homemanager.api.models.request.LoginRequest;
import com.example.homemanager.auth.models.requests.RegisterRequest;
import com.example.homemanager.auth.models.responses.TokenResponse;

public interface IAuthService {

    TokenResponse register(RegisterRequest request);

    TokenResponse login(LoginRequest request);

    TokenResponse refresh(String autHeader);

}
