package com.example.homemanager.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShoppingItemRequest {

    private String houseId;
    private String itemName;
    private int quantity;

}
