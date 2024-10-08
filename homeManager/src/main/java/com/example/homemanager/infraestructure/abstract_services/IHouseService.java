package com.example.homemanager.infraestructure.abstract_services;

import com.example.homemanager.api.models.request.HouseRequest;
import com.example.homemanager.api.models.responses.HouseResponse;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;
import com.example.homemanager.api.models.responses.TaskResponse;

import java.util.Set;

public interface IHouseService extends CrudService<HouseRequest, HouseResponse, String> {
    Set<TaskResponse> getHouseTasks(String id);

    Set<ShoppingItemResponse> getHouseShoppingItems(String id);

    HouseResponse addMember(String idCasa, String idUser);
}
