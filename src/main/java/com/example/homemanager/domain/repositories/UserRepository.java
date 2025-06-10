package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {

    @Query("{ 'email': ?0 }")
    UserDocument findByEmail(String email);

    @Query("{ 'username': ?0 }")
    UserDocument findByUsername(String username);

    boolean existsByUsername(String username);
}
