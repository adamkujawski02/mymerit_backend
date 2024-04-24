package com.mymerit.mymerit.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mymerit.mymerit.domain.models.AuthProvider;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String imageBase64 = "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";
    private AuthProvider provider;
    private String providerId;
    private Integer points = 0;
    private String description;
    private String role;
    private Company company;
    private List<Solution> solutions;
}