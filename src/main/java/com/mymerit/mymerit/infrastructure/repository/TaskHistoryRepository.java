package com.mymerit.mymerit.infrastructure.repository;

import com.mymerit.mymerit.domain.entity.TaskHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskHistoryRepository extends MongoRepository<TaskHistory, String> {
    Optional<List<TaskHistory>> findByUserId(String userId);
}