package com.example.homemanager.api.controllers;

import com.example.homemanager.api.models.request.HouseRequest;
import com.example.homemanager.api.models.responses.HouseResponse;
import com.example.homemanager.api.models.responses.TaskResponse;
import com.example.homemanager.infraestructure.abstract_services.IHouseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "house")
@AllArgsConstructor
public class HouseController {

    private final IHouseService houseService;

    //TODO ver que fomra es la mejor para que el usuario logeado pueda hacer consultas que correspondan, y que otro usuario logeado no acceda a recursos de otro aún estando logeado

    @PostMapping
    public ResponseEntity<HouseResponse> post(@Valid @RequestBody HouseRequest request) {

        return ResponseEntity.ok(houseService.create(request));
    }

    @GetMapping("/getTasks/{id}")
    public ResponseEntity<Set<TaskResponse>> getHouseTasks(@PathVariable String id) {

        return ResponseEntity.ok(houseService.getHouseTasks(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseResponse> getHouse(@PathVariable String id) {

        return ResponseEntity.ok(houseService.read(id));
    }

    @PutMapping("/addMember/{id}")
    public ResponseEntity<HouseResponse> addMember(
            @PathVariable String id,
            @RequestParam("userId") String userId) {

        return ResponseEntity.ok(houseService.addMember(id,userId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteHouse(
            @PathVariable String id) {
        houseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
