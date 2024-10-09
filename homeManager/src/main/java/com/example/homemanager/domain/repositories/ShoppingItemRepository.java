package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.ShoppingItemDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ShoppingItemRepository extends MongoRepository<ShoppingItemDocument, String> {
        List<ShoppingItemDocument> findByPurchaseDateBefore(Date date);

    void deleteByPurchaseDateBefore(Date thresholdDate);
}
