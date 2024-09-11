package com.example.homeManager.domain.documents;

import com.example.homeManager.utils.EstadoTarea;
import com.example.homeManager.utils.Periodicidad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(value = "tareas")
public class TareaDocument implements Serializable {

    private String id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private Periodicidad periodicidad;
    private EstadoTarea estado;
    private String idUsuarioAsignado;
    private String idCasa;
    private Integer puntos;

}
