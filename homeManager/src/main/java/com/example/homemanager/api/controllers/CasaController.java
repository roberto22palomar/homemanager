package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.CasaRequest;
import com.example.homemanager.api.models.responses.CasaResponse;
import com.example.homemanager.api.models.responses.TareaResponse;
import com.example.homemanager.infraestructure.abstract_services.ICasaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "casa")
@AllArgsConstructor
public class CasaController {

    private final ICasaService casaService;

    @PostMapping
    public ResponseEntity<CasaResponse> post(@Valid @RequestBody CasaRequest request) {

        return ResponseEntity.ok(casaService.create(request));
    }

    @GetMapping("/getTareas/{id}")
    public ResponseEntity<Set<TareaResponse>> getTareasCasa(@PathVariable String id) {

        return ResponseEntity.ok(casaService.getTareasCasa(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasaResponse> getCasa(@PathVariable String id) {

        return ResponseEntity.ok(casaService.read(id));
    }

    @PutMapping("/addMiembro/{id}")
    public ResponseEntity<CasaResponse> addMiembro(
            @PathVariable String id,
            @RequestParam("idUser") String idUser) {

        return ResponseEntity.ok(casaService.addMember(id,idUser));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCasa(
            @PathVariable String id) {
        casaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
