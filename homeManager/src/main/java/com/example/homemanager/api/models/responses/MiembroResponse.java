package com.example.homeManager.api.models.responses;

import com.example.homeManager.domain.documents.CasaDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MiembroResponse {

    private String id;
    private String username;
    private String email;

}
