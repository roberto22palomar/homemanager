package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.InvitationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;

public interface InvitationRepository extends MongoRepository<InvitationDocument, String> {
    @Query("{ 'email': ?0 }")
    Set<InvitationDocument> findByEmail(String email);
}
