package com.example.homemanager.api.models.request;

import com.example.homemanager.utils.Periodicity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaskRequest {

    private String name;
    private String description;
    private LocalDateTime expirationDate;
    private Periodicity periodicity;
    @NotBlank(message = "The assigned user ID must not be blank")
    private String assignedMemberId;
    @NotBlank(message = "The house ID must not be blank")
    private String houseId;
    private Integer points;
}
