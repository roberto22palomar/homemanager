package com.example.homeManager.domain.repositories;

import com.example.homeManager.domain.documents.CasaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CasaRepository extends MongoRepository<CasaDocument, String> {

}
