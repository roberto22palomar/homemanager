package com.example.homemanager.infraestructure.abstract_services;

import com.example.homemanager.api.models.request.InvitationRequest;
import com.example.homemanager.api.models.responses.InvitationResponse;
import com.example.homemanager.utils.InvitationStatus;

import java.util.Set;

public interface IInvitationService extends CrudService<InvitationRequest, InvitationResponse, String> {

    InvitationResponse updateInvitationStatus(String id, InvitationStatus status);

    Set<InvitationResponse> getInvitations(String email);

}
