package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.ShoppingItemDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoppingItemRepository extends MongoRepository<ShoppingItemDocument, String> {

}
