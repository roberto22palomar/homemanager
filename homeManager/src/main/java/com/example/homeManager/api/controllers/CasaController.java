package com.example.homeManager.api.controllers;

import com.example.homeManager.api.models.request.CasaRequest;
import com.example.homeManager.api.models.responses.CasaResponse;
import com.example.homeManager.api.models.responses.TareaResponse;
import com.example.homeManager.infraestructure.abstract_services.ICasaService;
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

}
