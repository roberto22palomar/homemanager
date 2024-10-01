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
public class HouseRequest {

    private String name;
    private String creatorId;
    private Set<String> membersId;
    private Set<String> tasksId;

}
