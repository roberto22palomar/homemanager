package com.example.homemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories(basePackages = {"com.example.homemanager.domain.repositories", "com.example.homemanager.auth.repository", "com.example.homemanager.config.configurations.repositories"})
public class HomeManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeManagerApplication.class, args);
    }

}
