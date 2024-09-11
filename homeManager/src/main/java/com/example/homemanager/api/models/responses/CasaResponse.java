package com.example.homemanager.api.models.responses;

import com.example.homemanager.domain.documents.TareaDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CasaResponse {

    private String id;
    private String nombre;
    private Set<MiembroResponse> miembros;
    private Set<TareaDocument> tareas;

}
