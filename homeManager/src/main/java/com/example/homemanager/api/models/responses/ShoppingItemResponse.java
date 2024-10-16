package com.example.homemanager.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShoppingItemResponse {

    private String id;
    private String itemName;
    private int quantity;
    private boolean purchased;

}
