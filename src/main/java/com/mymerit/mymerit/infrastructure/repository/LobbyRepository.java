package com.mymerit.mymerit.infrastructure.repository;

import com.mymerit.mymerit.domain.entity.Lobby;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LobbyRepository extends MongoRepository<Lobby,String> {
    Optional<Lobby> findByRecipient_Id(String id);
    Optional<Lobby> findBySender_Id(String id);
}
