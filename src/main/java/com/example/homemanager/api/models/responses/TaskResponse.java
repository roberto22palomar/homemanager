package com.example.homemanager.api.models.responses;

import com.example.homemanager.utils.TaskStatus;
import com.example.homemanager.utils.Periodicity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaskResponse {

    private String id;
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;
    private Periodicity periodicity;
    private TaskStatus status;
    private MemberResponse assignedMember;
    private String houseId;
    private Integer points;

}
