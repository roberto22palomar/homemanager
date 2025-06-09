package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.HouseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends MongoRepository<HouseDocument, String> {

    @Query("{ '_id': ?0 }")
    @Update("{ '$pull': { 'shoppingItemsId': ?1 } }")
    void removeShoppingItemFromHouse(String houseId, String shoppingItemId);

}
