package com.example.homemanager.infraestructure.abstract_services;

import com.example.homemanager.api.models.request.UserRequest;
import com.example.homemanager.api.models.responses.UserResponse;

public interface IUserService extends CrudService<UserRequest, UserResponse, String> {
}
