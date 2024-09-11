package com.example.homemanager.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequest {

    private String username;
    private String password;
    private String email;
    private Set<String> idCasas;

}
