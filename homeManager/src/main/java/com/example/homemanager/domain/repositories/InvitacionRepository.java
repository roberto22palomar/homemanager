package com.example.homeManager.domain.repositories;

import com.example.homeManager.domain.documents.InvitacionDocument;
import com.example.homeManager.domain.documents.TareaDocument;
import com.example.homeManager.domain.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;

public interface InvitacionRepository extends MongoRepository<InvitacionDocument, String> {
    @Query("{ 'email': ?0 }")
    Set<InvitacionDocument> findByEmail(String email);
}
