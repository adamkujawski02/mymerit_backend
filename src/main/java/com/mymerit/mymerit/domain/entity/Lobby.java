package com.mymerit.mymerit.domain.entity;

import com.mymerit.mymerit.domain.models.LobbyStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("lobbies")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Lobby {
    @Id
    public String id;
    public String lobbyId;
    public User recipient;
    public User sender;
    public LobbyStatus status;


}
