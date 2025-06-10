package com.example.homemanager.domain.repositories;

import com.example.homemanager.domain.documents.TaskDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends MongoRepository<TaskDocument, String> {

    @Query("{ 'idCasa': ?0 }")
    List<TaskDocument> findHouseTasks(String casaId);

}
