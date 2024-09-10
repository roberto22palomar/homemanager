package com.example.homeManager.api.models.request;

import com.example.homeManager.domain.documents.TareaDocument;
import com.example.homeManager.domain.documents.UserDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CasaRequest {

    private String nombre;
    private Set<String> idMiembros;
    private Set<String> IdTareas;

}
