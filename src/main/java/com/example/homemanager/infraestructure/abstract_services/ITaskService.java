package com.example.homemanager.infraestructure.abstract_services;

import com.example.homemanager.api.models.request.TaskRequest;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.utils.TaskStatus;

public interface ITaskService extends CrudService<TaskRequest, TaskResponse, String> {

    TaskResponse updateStatus(String id, TaskStatus status);


}
