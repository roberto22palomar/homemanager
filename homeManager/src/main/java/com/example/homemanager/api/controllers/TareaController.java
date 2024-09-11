package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.TareaRequest;
import com.example.homemanager.api.models.responses.TareaResponse;
import com.example.homemanager.infraestructure.abstract_services.ITareaService;
import com.example.homemanager.utils.EstadoTarea;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "tarea")
@AllArgsConstructor
public class TareaController {

    private final ITareaService tareaService;

    @PostMapping
    public ResponseEntity<TareaResponse> post(@Valid @RequestBody TareaRequest request) {

        return ResponseEntity.ok(tareaService.create(request));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<TareaResponse> actualizarEstado(
            @PathVariable String id,
            @RequestParam("nuevoEstado") EstadoTarea nuevoEstado) {

        return ResponseEntity.ok(tareaService.updateEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarTarea(
            @PathVariable String id) {
        tareaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
