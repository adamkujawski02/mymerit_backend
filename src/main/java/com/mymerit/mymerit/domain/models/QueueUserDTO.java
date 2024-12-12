package com.mymerit.mymerit.domain.models;

import com.mymerit.mymerit.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class QueueUserDTO {

    private User user;
    private ProgrammingLanguage language;
}
