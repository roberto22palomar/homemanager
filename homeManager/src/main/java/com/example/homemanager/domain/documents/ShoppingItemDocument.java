package com.example.homemanager.domain.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(value = "shoppingItems")
public class ShoppingItemDocument implements Serializable {

    private String id;
    private String houseId;
    private String itemName;
    private int quantity;
    private boolean purchased;
    private Date purchaseDate;


}
