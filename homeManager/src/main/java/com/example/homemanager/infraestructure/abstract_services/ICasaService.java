package com.example.homeManager.infraestructure.abstract_services;

import com.example.homeManager.api.models.request.CasaRequest;
import com.example.homeManager.api.models.responses.CasaResponse;
import com.example.homeManager.api.models.responses.TareaResponse;

import java.util.Set;

public interface ICasaService extends CrudService<CasaRequest, CasaResponse, String> {
    Set<TareaResponse> getTareasCasa(String id);
}
