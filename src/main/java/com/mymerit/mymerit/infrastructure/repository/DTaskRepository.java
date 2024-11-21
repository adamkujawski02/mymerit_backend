package com.mymerit.mymerit.infrastructure.repository;

import com.mymerit.mymerit.domain.entity.DTask;
import com.mymerit.mymerit.domain.models.ProgrammingLanguage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Range;

import java.util.List;

@Repository
public interface DTaskRepository extends MongoRepository<DTask,String> {
    List<DTask> findAllByLanguageAndEloDifficultyBetween(ProgrammingLanguage programmingLanguage,Range<Integer> eloRange);


}
