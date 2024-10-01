package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.InvitationRequest;
import com.example.homemanager.api.models.responses.InvitationResponse;
import com.example.homemanager.infraestructure.abstract_services.IInvitationService;
import com.example.homemanager.utils.InvitationStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "invitation")
@AllArgsConstructor
public class InvitationController {

    private final IInvitationService invitationService;

    @PostMapping
    public ResponseEntity<InvitationResponse> post(@Valid @RequestBody InvitationRequest request) {

        return ResponseEntity.ok(invitationService.create(request));
    }

    @PutMapping
    public ResponseEntity<InvitationResponse> updateStatus(
            @RequestParam String id,
            @RequestParam("newStatus") InvitationStatus status) {

        return ResponseEntity.ok(invitationService.updateInvitationStatus(id, status));
    }

    @GetMapping("/getInvitations/{email}")
    public ResponseEntity<Set<InvitationResponse>> getInvitations(@PathVariable String email) {

        return ResponseEntity.ok(invitationService.getInvitations(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(
            @PathVariable String id) {
        invitationService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
