package com.example.homemanager.domain.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(value = "casas")
public class CasaDocument implements Serializable {

    private String id;
    private String nombre;
    private Set<String> idMiembros;
    private Set<String> idTareas;


}
