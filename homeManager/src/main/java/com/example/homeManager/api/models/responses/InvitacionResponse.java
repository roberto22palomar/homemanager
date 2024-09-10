package com.example.homeManager.api.models.responses;

import com.example.homeManager.utils.EstadoInvitacion;
import com.example.homeManager.utils.InvitacionVigente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InvitacionResponse {

    private String id;
    private String email;
    private String idCasa;
    private EstadoInvitacion estado;
    private InvitacionVigente vigente;


}
