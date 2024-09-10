package com.example.homeManager.infraestructure.abstract_services;

import com.example.homeManager.api.models.request.UserRequest;
import com.example.homeManager.api.models.responses.UserResponse;

public interface IUserService extends CrudService<UserRequest, UserResponse, String> {
}
