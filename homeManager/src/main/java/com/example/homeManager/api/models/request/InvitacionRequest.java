package com.example.homeManager.api.models.request;

import com.example.homeManager.utils.EstadoInvitacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InvitacionRequest {

    private String email;
    private String idCasa;


}
