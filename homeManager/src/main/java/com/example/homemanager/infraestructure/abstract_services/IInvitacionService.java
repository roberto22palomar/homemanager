package com.example.homemanager.infraestructure.abstract_services;

import com.example.homemanager.api.models.request.InvitacionRequest;
import com.example.homemanager.api.models.responses.InvitacionResponse;
import com.example.homemanager.utils.EstadoInvitacion;

import java.util.Set;

public interface IInvitacionService extends CrudService<InvitacionRequest, InvitacionResponse, String> {

    InvitacionResponse updateEstadoInvitacion(String id, EstadoInvitacion estado);

    Set<InvitacionResponse> getInvitaciones(String email);

}
