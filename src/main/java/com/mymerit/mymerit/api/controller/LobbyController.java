package com.mymerit.mymerit.api.controller;

import com.mymerit.mymerit.domain.entity.Lobby;
import com.mymerit.mymerit.domain.entity.User;
import com.mymerit.mymerit.domain.service.LobbyService;
import com.mymerit.mymerit.domain.service.UserDetailsImpl;
import com.mymerit.mymerit.infrastructure.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
public class LobbyController {

    private final LobbyService lobbyService;
    private final Queue<User> lobbyQueue;

    @Autowired
    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
        this.lobbyQueue = new ConcurrentLinkedQueue<>();
    }
    @MessageMapping("/findMatch")
    @SendTo("/queue/match")
    public Lobby manageQueue(@Payload Map<String, String> message) {
        return lobbyService.manageLobbyService(message);
    }

    @GetMapping("/maintainLobby")
    public ResponseEntity<Lobby> isInLobby(@RequestParam String id){
        System.out.printf("\n\n\n\n");
        System.out.println(id);
        System.out.printf("\n\n\n\n");

        if(lobbyService.maintainLobby(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lobbyService.maintainLobby(id).get());
    }
}