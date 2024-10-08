package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.ShoppingItemRequest;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;
import com.example.homemanager.auth.aspects.CheckHouseAccess;
import com.example.homemanager.domain.documents.ShoppingItemDocument;
import com.example.homemanager.domain.repositories.HouseRepository;
import com.example.homemanager.domain.repositories.ShoppingItemRepository;
import com.example.homemanager.infraestructure.abstract_services.IShoppingItemService;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
@CheckHouseAccess
public class ShoppingItemService implements IShoppingItemService {

    private final HouseRepository houseRepository;
    private final ShoppingItemRepository shoppingItemRepository;


    private ShoppingItemResponse entityToResponse(ShoppingItemDocument entity) {
        var response = new ShoppingItemResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public ShoppingItemResponse create(ShoppingItemRequest request) {

        ShoppingItemDocument shoppingItemToPersist = ShoppingItemDocument.builder()
                .houseId(request.getHouseId())
                .itemName(request.getItemName())
                .quantity(request.getQuantity())
                .purchased(false)
                .build();

        var shoppingItemPersisted = shoppingItemRepository.save(shoppingItemToPersist);

        var house = houseRepository.findById(shoppingItemPersisted.getHouseId()).orElseThrow(() -> new IdNotFoundException("House not found."));

        house.getShoppingItemsId().add(shoppingItemPersisted.getId());

        houseRepository.save(house);

        return entityToResponse(shoppingItemPersisted);

    }

    @Override
    public ShoppingItemResponse read(String s) {
        return null;
    }

    @Override
    public ShoppingItemResponse update(ShoppingItemRequest request, String id) {

        var shoppingItemToUpdate = ShoppingItemDocument.builder()
                .id(id)
                .itemName(request.getItemName())
                .houseId(request.getHouseId())
                .quantity(request.getQuantity())
                .purchased(request.isPurchased())
                .build();

        var shoppingItemUpdated = shoppingItemRepository.save(shoppingItemToUpdate);

        return entityToResponse(shoppingItemUpdated);


    }

    @Override
    public void delete(String s) {

    }
}
