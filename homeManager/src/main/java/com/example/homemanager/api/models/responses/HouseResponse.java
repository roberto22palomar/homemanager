package com.example.homemanager.api.models.responses;

import com.example.homemanager.domain.documents.TaskDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HouseResponse {

    private String id;
    private String name;
    private String creatorId;
    private Set<MemberResponse> members;
    private Set<TaskDocument> tasks;

}
