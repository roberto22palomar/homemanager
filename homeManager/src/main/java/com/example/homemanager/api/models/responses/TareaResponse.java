package com.example.homeManager.api.models.responses;

import com.example.homeManager.utils.EstadoTarea;
import com.example.homeManager.utils.Periodicidad;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TareaResponse {

    private String id;
    private String nombre;
    private String descripcion;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaCreacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaVencimiento;
    private Periodicidad periodicidad;
    private EstadoTarea estado;
    private MiembroResponse usuarioAsignado;
    private String idCasa;
    private Integer puntos;

}
