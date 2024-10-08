package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.ShoppingItemRequest;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;
import com.example.homemanager.infraestructure.abstract_services.IShoppingItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "shopping-item")
@AllArgsConstructor
public class ShoppingItemController {

    private final IShoppingItemService shoppingItemService;

    @PostMapping
    public ResponseEntity<ShoppingItemResponse> post(@Valid @RequestBody ShoppingItemRequest request) {

        return ResponseEntity.ok(shoppingItemService.create(request));
    }

    @PutMapping("/{id}/purchased")
    public ResponseEntity<ShoppingItemResponse> updateShoppingItem(@PathVariable("id") String id,
                                                                   @RequestBody ShoppingItemRequest request) {

        return ResponseEntity.ok(shoppingItemService.update(request, id));
    }

   /* @PutMapping
    public ResponseEntity<InvitationResponse> updateStatus(
            @RequestParam String id,
            @RequestParam("newStatus") InvitationStatus status) {

        return ResponseEntity.ok(invitationService.updateInvitationStatus(id, status));
    }

    @GetMapping("/getInvitations")
    public ResponseEntity<Set<InvitationResponse>> getInvitations() {

        return ResponseEntity.ok(invitationService.getInvitations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(
            @PathVariable String id) {
        invitationService.delete(id);
        return ResponseEntity.noContent().build();
    }*/


}
