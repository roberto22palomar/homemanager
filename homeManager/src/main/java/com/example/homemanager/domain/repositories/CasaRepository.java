package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.CasaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CasaRepository extends MongoRepository<CasaDocument, String> {

}
