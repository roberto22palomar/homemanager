package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.InvitationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface InvitationRepository extends MongoRepository<InvitationDocument, String> {
    @Query("{ 'invitedUser': ?0 }")
    Set<InvitationDocument> findByUsername(String username);
}
