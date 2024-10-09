package com.example.homemanager.infraestructure.services;

import com.example.homemanager.domain.documents.Configuration;
import com.example.homemanager.domain.repositories.ConfigurationRepository;
import com.example.homemanager.utils.exceptions.IdNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@Slf4j
public class ConfigurationService {

    private ConfigurationRepository configurationRepository;
    private ObjectMapper objectMapper;


    public <T> T getConfiguration(String key, Class<T> clazz) throws Exception {
        Optional<Configuration> configOpt = configurationRepository.findByKey(key);

        if (configOpt.isPresent()) {
            String value = configOpt.get().getValue();
            return objectMapper.readValue(value, clazz);
        }
        throw new IdNotFoundException("Configuration not found for key: " + key);
    }
}
