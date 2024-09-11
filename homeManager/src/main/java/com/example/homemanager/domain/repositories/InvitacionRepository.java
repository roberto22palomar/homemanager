package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.InvitacionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;

public interface InvitacionRepository extends MongoRepository<InvitacionDocument, String> {
    @Query("{ 'email': ?0 }")
    Set<InvitacionDocument> findByEmail(String email);
}
