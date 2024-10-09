package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.Configuration;
import com.example.homemanager.domain.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
    Optional<Configuration> findByKey(String key); // Método para buscar por key

}