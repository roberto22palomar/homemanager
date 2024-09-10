package com.example.homeManager.domain.repositories;

import com.example.homeManager.domain.documents.InvitacionDocument;
import com.example.homeManager.domain.documents.TareaDocument;
import com.example.homeManager.domain.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InvitacionRepository extends MongoRepository<InvitacionDocument,String> {

}
