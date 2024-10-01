package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.TaskRequest;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.infraestructure.abstract_services.ITaskService;
import com.example.homemanager.utils.TaskStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "task")
@AllArgsConstructor
public class TaskController {

    private final ITaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> post(@Valid @RequestBody TaskRequest request) {

        return ResponseEntity.ok(taskService.create(request));
    }

    @PutMapping
    public ResponseEntity<TaskResponse> updateStatus(
            @RequestParam String id,
            @RequestParam("newStatus") TaskStatus status) {

        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable String id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
