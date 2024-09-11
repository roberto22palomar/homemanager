package com.example.homemanager.domain.documents;

import com.example.homemanager.utils.EstadoInvitacion;
import com.example.homemanager.utils.InvitacionVigente;
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
@Document(value = "invitaciones")
public class InvitacionDocument implements Serializable {

    private String id;
    private String email;
    private String idCasa;
    private EstadoInvitacion estado;
    private InvitacionVigente vigente;




}
