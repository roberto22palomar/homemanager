package com.example.homemanager.api.models.request;

import com.example.homemanager.utils.Periodicidad;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TareaRequest {

    private String nombre;
    private String descripcion;
    private LocalDateTime fechaVencimiento;
    private Periodicidad periodicidad;
    @NotBlank(message = "El ID del usuario asignado no debe estar en blanco")
    private String idUsuarioAsignado;
    @NotBlank(message = "El ID de la casa no debe estar en blanco")
    private String idCasa;
    private Integer puntos;
}
