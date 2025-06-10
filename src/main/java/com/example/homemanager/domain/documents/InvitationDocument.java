package com.example.homemanager.domain.documents;

import com.example.homemanager.utils.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(value = "invitations")
public class InvitationDocument implements Serializable {

    private String id;
    private String invitedUser;
    private String invitingUser;
    private String houseId;
    private InvitationStatus status;
    private boolean revoked;




}
