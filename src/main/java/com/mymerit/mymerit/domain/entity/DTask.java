package com.mymerit.mymerit.domain.entity;

import com.mymerit.mymerit.domain.models.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("dTasks")
@AllArgsConstructor
@Getter
@Setter

public class DTask {

    @Id
    private String id;
    private String instructions;
    private String title;
    private ProgrammingLanguage language;
    private List<TemplateFile> templateFiles = new ArrayList<>();
    private Integer memoryLimit;
    private Float timeLimit;
    public List<CodeTest> tests = new ArrayList<>();
    @DBRef
    private List<Solution> solutions = new ArrayList<>();
    private Integer eloDifficulty;

}
