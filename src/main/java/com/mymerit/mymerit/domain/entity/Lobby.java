package com.mymerit.mymerit.domain.entity;

import com.mymerit.mymerit.domain.models.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("lobbies")
@Data
public class Lobby {
    @Id
    private String id;
    private String lobbyId;
    private User senderUser;
    private User recipientUser;
    private Status status;
    private LocalDateTime createdAt;
}
