package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.HouseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseRepository extends MongoRepository<HouseDocument, String> {

}
