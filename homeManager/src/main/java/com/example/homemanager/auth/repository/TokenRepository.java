package com.example.homemanager.auth.repository;

import com.example.homemanager.auth.models.TokenDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<TokenDocument, String> {
    @Query("{ 'user.id': ?0, 'revoked': false, 'expired': false }")
    List<TokenDocument> findAllValidTokensByUserId(String userId);
    Optional<TokenDocument> findByToken(String token);
}
