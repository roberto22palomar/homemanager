package com.example.homemanager.config.configurations.repositories;

import com.example.homemanager.config.configurations.models.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
    Optional<Configuration> findByKey(String key); // Método para buscar por key

}