package com.mymerit.mymerit.domain.entity;

import com.mymerit.mymerit.domain.models.ProgrammingLanguage;
import com.mymerit.mymerit.domain.models.QueueUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Code;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
public class CodeWarsQueue {

    private Map<ProgrammingLanguage, List<QueueUserDTO>> userQueueMap = new HashMap<>();




    public void addUserToQueue(User user, ProgrammingLanguage language) {

        userQueueMap
                .computeIfAbsent(language, k -> new LinkedList<>())
                .add(userToDTO(user, language));
    }




    public User pollOpponent(User user, ProgrammingLanguage language) {

        addUserToQueue(user, language);


        List<QueueUserDTO> languageQueue = userQueueMap.get(language);


        if (languageQueue.size() > 1) {
            QueueUserDTO userDTO = userToDTO(user, language);
            for (QueueUserDTO queuedUser : languageQueue) {
                if (!queuedUser.getUser().equals(user)) {

                    languageQueue.remove(queuedUser);
                    languageQueue.remove(userDTO);


                    if (languageQueue.isEmpty()) {
                        userQueueMap.remove(language);
                    }

                    return queuedUser.getUser();
                }
            }
        }


        return null;
    }


    private QueueUserDTO userToDTO(User user, ProgrammingLanguage language){
        return new QueueUserDTO(user,language);
    }

}
