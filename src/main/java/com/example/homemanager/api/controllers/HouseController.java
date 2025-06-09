package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.HouseRequest;
import com.example.homemanager.api.models.responses.HouseResponse;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.infraestructure.abstract_services.IHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "house")
@AllArgsConstructor
@Tag(name = "House Management", description = "Operations related to house management")
public class HouseController {

    private final IHouseService houseService;

    @Operation(summary = "Crea una nueva casa.", description = "Retorna datos de la casa creada.")
    @PostMapping
    public ResponseEntity<HouseResponse> post(
            @Valid
            @RequestBody HouseRequest request) {

        return ResponseEntity.ok(houseService.create(request));
    }

    @Operation(summary = "Consulta las tareas de la casa.", description = "Retorna las tareas asignadas a la casa consultada.")
    @GetMapping("/getTasks/{id}")
    public ResponseEntity<Set<TaskResponse>> getHouseTasks(
            @Parameter(description = "Id de la casa.", required = true)
            @PathVariable String id) {

        return ResponseEntity.ok(houseService.getHouseTasks(id));
    }

    @Operation(summary = "Consulta los productos de la lista de la compra de la casa.", description = "Retorna los items a comprar de la casa consultada.")
    @GetMapping("/getShoppingItems/{id}")
    public ResponseEntity<Set<ShoppingItemResponse>> getHouseShoppingItems(
            @Parameter(description = "Id de la casa.", required = true)
            @PathVariable String id) {

        return ResponseEntity.ok(houseService.getHouseShoppingItems(id));
    }

    @Operation(summary = "Consulta información de la casa.", description = "Retorna información de la casa consultada.")
    @GetMapping("/{id}")
    public ResponseEntity<HouseResponse> getHouse(
            @Parameter(description = "Id de la casa.", required = true)
            @PathVariable String id) {

        return ResponseEntity.ok(houseService.read(id));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Borra la casa.", description = "Borra la casa indicada.")

    public ResponseEntity<Void> deleteHouse(
            @Parameter(description = "Id de la casa.", required = true)
            @PathVariable String id) {
        houseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
