package com.example.homemanager.infraestructure.abstract_services;

import com.example.homemanager.api.models.request.TareaRequest;
import com.example.homemanager.api.models.responses.TareaResponse;
import com.example.homemanager.utils.EstadoTarea;

public interface ITareaService extends CrudService<TareaRequest, TareaResponse, String> {

    TareaResponse updateEstado(String id, EstadoTarea estado);


}
