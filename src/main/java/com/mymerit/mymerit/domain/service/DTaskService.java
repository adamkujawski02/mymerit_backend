package com.mymerit.mymerit.domain.service;

import com.mymerit.mymerit.domain.entity.DTask;
import com.mymerit.mymerit.domain.models.ProgrammingLanguage;
import com.mymerit.mymerit.infrastructure.repository.DTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Range;

import java.util.ArrayList;
import java.util.List;

@Service
public class DTaskService {
    DTaskRepository dTaskRepository;

    @Autowired
    public DTaskService(DTaskRepository dTaskRepository){
       this.dTaskRepository = dTaskRepository;
   }

   public void addDTask(DTask dTask){
        dTaskRepository.save(dTask);
   }

   public void deleteDTask(String id){
        dTaskRepository.delete(dTaskRepository.findById(id).get());
   }

   public List<DTask> defineDTaskList(ProgrammingLanguage language ,Range<Integer> range){

        return dTaskRepository.findAllByLanguageAndEloDifficultyBetween(language, range).stream().limit(5).toList();
   }


}
