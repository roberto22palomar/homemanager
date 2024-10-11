package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.ShoppingItemDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface ShoppingItemRepository extends MongoRepository<ShoppingItemDocument, String> {
        List<ShoppingItemDocument> findByPurchaseDateBefore(Date date);

    void deleteByPurchaseDateBefore(Date thresholdDate);
}
