package com.mymerit.mymerit.domain.service;

import com.mymerit.mymerit.domain.entity.JobOffer;
import com.mymerit.mymerit.domain.entity.Lobby;
import com.mymerit.mymerit.domain.entity.Task;
import com.mymerit.mymerit.domain.entity.User;
import com.mymerit.mymerit.domain.models.LobbyStatus;
import com.mymerit.mymerit.domain.models.OfferType;
import com.mymerit.mymerit.infrastructure.repository.JobOfferRepository;
import com.mymerit.mymerit.infrastructure.repository.LobbyRepository;
import com.mymerit.mymerit.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class LobbyService {

    private final JobOfferRepository jobOfferRepository;
    UserRepository userRepository;
    LobbyRepository lobbyRepository;
    Queue<User> lobbyQueue;
    JobOfferService jobOfferService;

    @Autowired
    public LobbyService(UserRepository userRepository , LobbyRepository lobbyRepository, JobOfferService jobOfferService, JobOfferRepository jobOfferRepository){
        this.userRepository = userRepository;
        this.lobbyRepository = lobbyRepository;
        this.lobbyQueue = new ConcurrentLinkedQueue<>();
        this.jobOfferService = jobOfferService;
        this.jobOfferRepository = jobOfferRepository;
    }

    public Optional<User> findById(String id){
        return userRepository.findById(id);
    }

    public Lobby createLobby(User sender, User recipient) {

        Lobby lobby = new Lobby();
        lobby.setLobbyId("rand");
        lobby.setSender(sender);
        lobby.setRecipient(recipient);
        lobby.setStatus(LobbyStatus.ONGOING);
        lobby.setTask(findTask());

        return lobbyRepository.save(lobby);
    }

    public Lobby manageLobbyService(Map<String,String> message){

        String id = message.get("id");


        if(id == null || id.isEmpty()){
            System.out.println("Id not provided");
            return null;
        }


        Optional<User> currentUser = userRepository.findById(id);
        if(!userRepository.findById(id).isPresent()){
            System.out.println("User does not exist");
            return null;
        }
        else{
            User user = currentUser.get();
        }


        synchronized (lobbyQueue) {
            User user = currentUser.get();
            if (!lobbyQueue.contains(user)) {
                lobbyQueue.add(user);
                System.out.println(lobbyQueue);
            }

            User opponent = lobbyQueue.stream()
                    .filter(u -> !u.equals(currentUser.get()))
                    .filter(u -> Math.abs(u.getEloRating() - currentUser.get().getEloRating()) <= 200)
                    .findFirst()
                    .orElse(null);

            if (opponent != null) {
                lobbyQueue.remove(user);
                lobbyQueue.remove(opponent);

                return createLobby(user, opponent);
            }
        }
        return null;


    }


    public Task findTask(){
       JobOffer offer = jobOfferRepository.findByOfferType(OfferType.CODE_TASK);

       return offer.getTask();
    }

    public Optional<Lobby> maintainLobby(String id){
        if(lobbyRepository.findBySender_Id(id).isPresent()){

            return lobbyRepository.findBySender_Id(id);
        }
        if(lobbyRepository.findByRecipient_Id(id).isPresent()){

            return lobbyRepository.findByRecipient_Id(id);
        }
       return Optional.empty();
    }

}