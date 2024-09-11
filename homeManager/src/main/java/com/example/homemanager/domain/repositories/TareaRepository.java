package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.TareaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TareaRepository extends MongoRepository<TareaDocument, String> {

    @Query("{ 'idCasa': ?0 }")
    List<TareaDocument> findTareasCasa(String casaId);

}
