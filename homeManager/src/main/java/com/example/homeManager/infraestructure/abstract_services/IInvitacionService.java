package com.example.homeManager.infraestructure.abstract_services;

import com.example.homeManager.api.models.request.InvitacionRequest;
import com.example.homeManager.api.models.responses.InvitacionResponse;
import com.example.homeManager.utils.EstadoInvitacion;

import java.util.Set;

public interface IInvitacionService extends CrudService<InvitacionRequest, InvitacionResponse, String> {

    InvitacionResponse updateEstadoInvitacion(String id, EstadoInvitacion estado);

    Set<InvitacionResponse> getInvitaciones(String email);

}
