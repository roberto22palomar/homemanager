package com.example.homemanager.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private Set<String> housesId;

}
