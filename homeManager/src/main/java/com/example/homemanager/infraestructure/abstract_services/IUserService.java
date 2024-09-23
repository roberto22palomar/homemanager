package com.example.homemanager.infraestructure.abstract_services;

import com.example.homemanager.api.models.request.LoginRequest;
import com.example.homemanager.auth.models.requests.RegisterRequest;
import com.example.homemanager.api.models.responses.UserResponse;

public interface IUserService extends CrudService<RegisterRequest, UserResponse, String> {
}
