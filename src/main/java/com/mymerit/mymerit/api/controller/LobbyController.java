package com.mymerit.mymerit.api.controller;


import com.mymerit.mymerit.domain.entity.Lobby;
import com.mymerit.mymerit.domain.entity.User;
import com.mymerit.mymerit.domain.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Controller
@RequestMapping("/wars")
public class LobbyController {

    LobbyService lobbyService;
    private final Queue<User> waitingQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    public LobbyController(LobbyService lobbyService){
        this.lobbyService = lobbyService;
    }


    @PostMapping("/find")
    public synchronized Lobby findOpponent(@RequestBody User user) {

        User opponent = waitingQueue.poll();

        if (opponent != null) {

            Lobby l = lobbyService.createLobby(user, opponent);
            return l;
        } else {

            waitingQueue.add(user);
            throw new IllegalStateException("Waiting for an opponent...");
        }
    }

}
