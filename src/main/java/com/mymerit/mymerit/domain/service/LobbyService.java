package com.mymerit.mymerit.domain.service;

import com.mymerit.mymerit.domain.entity.Lobby;
import com.mymerit.mymerit.domain.entity.User;
import com.mymerit.mymerit.domain.models.LobbyStatus;
import com.mymerit.mymerit.infrastructure.repository.LobbyRepository;
import com.mymerit.mymerit.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LobbyService {

    UserRepository userRepository;
    LobbyRepository lobbyRepository;

    @Autowired
    public LobbyService(UserRepository userRepository , LobbyRepository lobbyRepository){
        this.userRepository = userRepository;
        this.lobbyRepository = lobbyRepository;
    }

    public Optional<User> extractUser(UserDetailsImpl user){
        return userRepository.findById(user.getId());
    }

    public Lobby createLobby(User sender, User recipient) {

        Lobby lobby = new Lobby();
        lobby.setLobbyId("rand");
        lobby.setSender(sender);
        lobby.setRecipient(recipient);
        lobby.setStatus(LobbyStatus.ONGOING);

        return lobbyRepository.save(lobby);
    }
}
