package com.example.homemanager.api.models.responses;

import com.example.homemanager.utils.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InvitationResponse {

    private String id;
    private String invitedUser;
    private String invitingUser;
    private String houseId;
    private InvitationStatus status;
    private boolean revoked;


}
