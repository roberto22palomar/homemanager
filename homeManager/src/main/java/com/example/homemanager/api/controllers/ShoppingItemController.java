package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.ShoppingItemRequest;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;
import com.example.homemanager.infraestructure.abstract_services.IShoppingItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "shopping-item")
@AllArgsConstructor
@Tag(name = "Shopping Items Management", description = "Operations related to shopping items management")
public class ShoppingItemController {

    private final IShoppingItemService shoppingItemService;

    @Operation(summary = "Crea un item de la compra.", description = "Crea y asocia el item a la casa.")
    @PostMapping
    public ResponseEntity<ShoppingItemResponse> post(@Valid @RequestBody ShoppingItemRequest request) {

        return ResponseEntity.ok(shoppingItemService.create(request));
    }
    @Operation(summary = "Actualiza el estado del item.", description = "Actualiza el estado del item.")
    @PutMapping("/{id}/purchased")
    public ResponseEntity<ShoppingItemResponse> updateShoppingItem(
            @Parameter(description = "Id del item.", required = true)
            @PathVariable("id") String id,
            @RequestBody ShoppingItemRequest request) {

        return ResponseEntity.ok(shoppingItemService.update(request, id));
    }

}
