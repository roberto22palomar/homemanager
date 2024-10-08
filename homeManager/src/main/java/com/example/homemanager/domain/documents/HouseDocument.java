package com.example.homemanager.domain.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(value = "houses")
public class HouseDocument implements Serializable {

    private String id;
    private String name;
    private String creatorId;
    private Set<String> membersId;
    private Set<String> tasksId;
    private Set<String> shoppingItemsId;
    private Map<String,Integer> points;


}
