package com.example.homemanager.domain.documents;

import com.example.homemanager.utils.TaskStatus;
import com.example.homemanager.utils.Periodicity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(value = "tasks")
public class TaskDocument implements Serializable {

    private String id;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
    private Periodicity periodicity;
    private TaskStatus status;
    private String assignedUserId;
    private String houseId;
    private Integer points;
    private boolean assignedPoints;

}
