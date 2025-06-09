package com.example.homemanager.api.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HouseRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String address;

}
