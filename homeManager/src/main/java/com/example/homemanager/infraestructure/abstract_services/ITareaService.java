package com.example.homeManager.infraestructure.abstract_services;

import com.example.homeManager.api.models.request.TareaRequest;
import com.example.homeManager.api.models.responses.TareaResponse;
import com.example.homeManager.utils.EstadoTarea;

public interface ITareaService extends CrudService<TareaRequest, TareaResponse, String> {

    TareaResponse updateEstado(String id, EstadoTarea estado);


}
