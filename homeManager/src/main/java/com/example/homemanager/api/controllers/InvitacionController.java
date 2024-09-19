package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.InvitacionRequest;
import com.example.homemanager.api.models.responses.InvitacionResponse;
import com.example.homemanager.infraestructure.abstract_services.IInvitacionService;
import com.example.homemanager.utils.EstadoInvitacion;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "invitacion")
@AllArgsConstructor
public class InvitacionController {

    private final IInvitacionService invitacionService;

    @PostMapping
    public ResponseEntity<InvitacionResponse> post(@Valid @RequestBody InvitacionRequest request) {

        return ResponseEntity.ok(invitacionService.create(request));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<InvitacionResponse> actualizarEstado(
            @PathVariable String id,
            @RequestParam("nuevoEstado") EstadoInvitacion estado) {

        return ResponseEntity.ok(invitacionService.updateEstadoInvitacion(id, estado));
    }

    @GetMapping("/getInvitaciones/{email}")
    public ResponseEntity<Set<InvitacionResponse>> getInvitaciones(@PathVariable String email) {

        return ResponseEntity.ok(invitacionService.getInvitaciones(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarInvitacion(
            @PathVariable String id) {
        invitacionService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
