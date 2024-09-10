package com.example.homeManager.api.controllers;

import com.example.homeManager.api.models.request.InvitacionRequest;
import com.example.homeManager.api.models.responses.InvitacionResponse;
import com.example.homeManager.api.models.responses.TareaResponse;
import com.example.homeManager.infraestructure.abstract_services.IInvitacionService;
import com.example.homeManager.utils.EstadoInvitacion;
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


}
