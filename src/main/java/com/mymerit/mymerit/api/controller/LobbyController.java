package com.mymerit.mymerit.api.controller;

import com.mymerit.mymerit.domain.entity.Lobby;
import com.mymerit.mymerit.domain.entity.User;
import com.mymerit.mymerit.domain.service.LobbyService;
import com.mymerit.mymerit.domain.service.UserDetailsImpl;
import com.mymerit.mymerit.infrastructure.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Controller
@RequestMapping("/lobby")
public class LobbyController {

    private final LobbyService lobbyService;
    private final Queue<User> lobbyQueue;

    @Autowired
    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
        this.lobbyQueue = new ConcurrentLinkedQueue<>();
    }

    // WebSocketowa metoda do dołączenia do kolejki
    @MessageMapping("/joinQueue")
    public boolean joinQueue(@CurrentUser UserDetailsImpl user) {
        User currentUser = lobbyService.extractUser(user).orElseThrow(() -> new IllegalStateException("User not found"));

        if (!lobbyQueue.contains(currentUser)) {
            lobbyQueue.add(currentUser);
            return true;
        }
        return false;
    }

    // WebSocketowa metoda do szukania przeciwnika
    @MessageMapping("/findOpponent")
    @SendTo("/queue/match")
    public Lobby findOpponent(@CurrentUser UserDetailsImpl user) {
        User currentUser = lobbyService.extractUser(user).orElseThrow(() -> new IllegalStateException("User not found"));

        if (!lobbyQueue.contains(currentUser)) {
            throw new IllegalStateException("User not in queue.");
        }

        User opponent = lobbyQueue.stream().filter(u -> !u.equals(currentUser)).findFirst().orElse(null);

        if (opponent != null) {
            lobbyQueue.remove(currentUser);
            lobbyQueue.remove(opponent);

            return lobbyService.createLobby(currentUser, opponent);
        } else {
            return null; // Możesz dodać bardziej odpowiedni komunikat
        }
    }
}
