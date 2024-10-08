package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.ShoppingItemRequest;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;
import com.example.homemanager.domain.documents.ShoppingItemDocument;
import com.example.homemanager.infraestructure.abstract_services.IShoppingItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ShoppingItemService implements IShoppingItemService {





    private ShoppingItemResponse entityToResponse(ShoppingItemDocument entity) {
        var response = new ShoppingItemResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public ShoppingItemResponse create(ShoppingItemRequest request) {
        return null;
    }

    @Override
    public ShoppingItemResponse read(String s) {
        return null;
    }

    @Override
    public ShoppingItemResponse update(ShoppingItemRequest request, String s) {
        return null;
    }

    @Override
    public void delete(String s) {

    }
}
