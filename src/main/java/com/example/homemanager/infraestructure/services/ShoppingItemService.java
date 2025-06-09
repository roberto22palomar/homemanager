package com.example.homemanager.infraestructure.services;

import com.example.homemanager.api.models.request.ShoppingItemRequest;
import com.example.homemanager.api.models.responses.ShoppingItemResponse;
import com.example.homemanager.auth.aspects.CheckHouseAccess;
import com.example.homemanager.config.configurations.models.ShoppingItemCleanupConfig;
import com.example.homemanager.config.configurations.services.ConfigurationService;
import com.example.homemanager.domain.documents.HouseDocument;
import com.example.homemanager.domain.documents.ShoppingItemDocument;
import com.example.homemanager.domain.repositories.HouseRepository;
import com.example.homemanager.domain.repositories.ShoppingItemRepository;
import com.example.homemanager.infraestructure.abstract_services.IShoppingItemService;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
@CheckHouseAccess
public class ShoppingItemService implements IShoppingItemService {

    private final HouseRepository houseRepository;
    private final ShoppingItemRepository shoppingItemRepository;
    private final ConfigurationService configurationService;

    private ShoppingItemResponse entityToResponse(ShoppingItemDocument entity) {
        var response = new ShoppingItemResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public ShoppingItemResponse create(ShoppingItemRequest request) {

        HouseDocument house = houseRepository.findById(request.getHouseId())
                .orElseThrow(() -> new IdNotFoundException(HouseDocument.class.getSimpleName(), request.getHouseId()));

        ShoppingItemDocument shoppingItemToPersist = ShoppingItemDocument.builder()
                .houseId(request.getHouseId())
                .itemName(request.getItemName())
                .quantity(request.getQuantity())
                .purchased(request.isPurchased())
                .build();

        var shoppingItemPersisted = shoppingItemRepository.save(shoppingItemToPersist);

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

        if (request.isPurchased()) {
            shoppingItemToUpdate.setPurchaseDate(Date.from(Instant.now()));
        }

        var shoppingItemUpdated = shoppingItemRepository.save(shoppingItemToUpdate);

        return entityToResponse(shoppingItemUpdated);


    }

    @Override
    public void delete(String id) {
        shoppingItemRepository.deleteById(id);
    }


    @Scheduled(cron = "0 1 * * * *")
    public void cleanupPurchasedItems() {

        log.info(">>> Executing Cron: {shoppingItemCleanup} ...");

        ShoppingItemCleanupConfig config = configurationService.getConfiguration("shoppingItemCleanup", ShoppingItemCleanupConfig.class)
                .orElse(new ShoppingItemCleanupConfig());

        int daysToKeep = config.getDaysToKeepPurchased();
        Date thresholdDate = Date.from(Instant.now().minusSeconds((long) daysToKeep * 24 * 60 * 60));

        var shoppingItemsOld = shoppingItemRepository.findByPurchaseDateBefore(thresholdDate);

        shoppingItemsOld.forEach(shoppingItemDocument -> {
            houseRepository.removeShoppingItemFromHouse(shoppingItemDocument.getHouseId(), shoppingItemDocument.getId());
            shoppingItemRepository.delete(shoppingItemDocument);
        });

        log.info("<<< Deleting {} shopping items that have been purchased for more than {} days.", shoppingItemsOld.size(), daysToKeep);

    }
}
