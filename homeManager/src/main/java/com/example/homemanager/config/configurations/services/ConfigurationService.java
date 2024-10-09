package com.example.homemanager.config.configurations.services;

import com.example.homemanager.config.configurations.repositories.ConfigurationRepository;
import com.example.homemanager.config.configurations.models.Configuration;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfigurationService {

    private ConfigurationRepository configurationRepository;
    private  ObjectMapper objectMapper;

    public <T> Optional<T> getConfiguration(String key, Class<T> valueType) {
        Optional<Configuration> configDoc = configurationRepository.findByKey(key);
        if (configDoc.isPresent()) {
            String jsonValue = configDoc.get().getValue();
            try {
                T configValue = objectMapper.readValue(jsonValue, valueType);
                return Optional.of(configValue);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error deserialization configurationn", e);
            }
        }
        return Optional.empty();
    }
}
