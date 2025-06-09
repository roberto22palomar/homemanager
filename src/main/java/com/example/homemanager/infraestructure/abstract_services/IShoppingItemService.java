package com.example.homemanager.infraestructure.abstract_services;

import com.example.homemanager.api.models.request.ShoppingItemRequest;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;

public interface IShoppingItemService extends CrudService<ShoppingItemRequest, ShoppingItemResponse, String> {
}
