package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.InvitationRequest;
import com.example.homemanager.api.models.responses.InvitationResponse;
import com.example.homemanager.infraestructure.abstract_services.IInvitationService;
import com.example.homemanager.utils.InvitationStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "invitation")
@AllArgsConstructor
@Tag(name = "Invitations Management", description = "Operations related to invitations management")
public class InvitationController {

    private final IInvitationService invitationService;

    @Operation(summary = "Crea una invitación a la casa.", description = "Crea una invitación para el usuario indicado a la casa indicada.")
    @PostMapping
    public ResponseEntity<InvitationResponse> post(
            @Valid
            @RequestBody InvitationRequest request) {

        return ResponseEntity.ok(invitationService.create(request));
    }

    @Operation(summary = "Actualiza el estado de la invitación.", description = "Actualiza el estado de una invitación a 'revocada' y dependiendo del estado añade al usuario a la casa invitada.")
    @PutMapping
    public ResponseEntity<InvitationResponse> updateStatus(
            @Parameter(description = "Id de la invitación.", required = true)
            @RequestParam String id,
            @Parameter(description = "Estado de la respuesta de la invitación.", required = true)
            @RequestParam("newStatus") InvitationStatus status) {

        return ResponseEntity.ok(invitationService.updateInvitationStatus(id, status));
    }

    @Operation(summary = "Consulta las invitaciones del usuario logeado.", description = "Consulta todas las invitaciones relacionadas con el usuario logeado.")
    @GetMapping("/getInvitations")
    public ResponseEntity<Set<InvitationResponse>> getInvitations() {

        return ResponseEntity.ok(invitationService.getInvitations());
    }

    @Operation(summary = "Borra la invitación.", description = "Borra la invitación que se indique.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(
            @Parameter(description = "Id de la invitación.", required = true)
            @PathVariable String id) {
        invitationService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
