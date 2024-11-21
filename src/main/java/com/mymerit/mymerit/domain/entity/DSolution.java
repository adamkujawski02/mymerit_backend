package com.mymerit.mymerit.domain.entity;
import com.mymerit.mymerit.api.payload.response.TestResponse;
import com.mymerit.mymerit.domain.models.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("dSolutions")
@AllArgsConstructor
@Getter
@Setter

public class DSolution {
    @DBRef
    @JsonIgnore
    private DTask dTask;
    private User user;
    private List<String> files;
    private String mainFileId;
    private ProgrammingLanguage language;
    private List<TestResponse> testResults = new ArrayList<>();
}
