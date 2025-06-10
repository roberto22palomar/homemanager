package com.example.homemanager.config.configurations.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(value = "configurations")
public class Configuration implements Serializable {

    @Id
    private String id;
    private String key;
    private String description;
    private String value;

}
