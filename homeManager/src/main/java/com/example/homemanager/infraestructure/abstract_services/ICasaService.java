package com.example.homemanager.infraestructure.abstract_services;

import com.example.homemanager.api.models.request.CasaRequest;
import com.example.homemanager.api.models.responses.CasaResponse;
import com.example.homemanager.api.models.responses.TareaResponse;

import java.util.Set;

public interface ICasaService extends CrudService<CasaRequest, CasaResponse, String> {
    Set<TareaResponse> getTareasCasa(String id);
}
