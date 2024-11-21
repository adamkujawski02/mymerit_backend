package com.mymerit.mymerit.domain.service;

import com.mymerit.mymerit.domain.entity.Lobby;
import com.mymerit.mymerit.domain.entity.User;
import com.mymerit.mymerit.domain.models.Status;
import com.mymerit.mymerit.infrastructure.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LobbyService {

    LobbyRepository lobbyRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository){
        this.lobbyRepository = lobbyRepository;
    }

    public Lobby createLobby(User sender, User recipient) {

        Lobby lobby = new Lobby();
        lobby.setLobbyId("LOBBY-" + UUID.randomUUID());
        lobby.setSenderUser(sender);
        lobby.setRecipientUser(recipient);
        lobby.setStatus(Status.ACTIVE);
        lobby.setCreatedAt(LocalDateTime.now());


        return lobbyRepository.save(lobby);
    }

}
