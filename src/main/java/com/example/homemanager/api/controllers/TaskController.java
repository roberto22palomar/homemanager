package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.TaskRequest;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.infraestructure.abstract_services.ITaskService;
import com.example.homemanager.utils.TaskStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "task")
@AllArgsConstructor
@Tag(name = "Task Management", description = "Operations related to task management")
public class TaskController {

    private final ITaskService taskService;

    @Operation(summary = "Crea una tarea.", description = "Crea y asocia una tarea a la casa.")
    @PostMapping
    public ResponseEntity<TaskResponse> post(@Valid @RequestBody TaskRequest request) {

        return ResponseEntity.ok(taskService.create(request));
    }
    @Operation(summary = "Actualiza el estado de una tarea.", description = "Actualiza el estado de una tarea.")
    @PutMapping
    public ResponseEntity<TaskResponse> updateStatus(
            @Parameter(description = "Id de la tarea.", required = true)
            @RequestParam String id,
            @Parameter(description = "Estado de la tarea.", required = true)
            @RequestParam("newStatus") TaskStatus status) {

        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }

    @Operation(summary = "Borra una tarea.", description = "Borra el item de tarea y también la referencia en la casa.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable String id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
