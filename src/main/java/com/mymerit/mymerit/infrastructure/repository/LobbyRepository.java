package com.mymerit.mymerit.infrastructure.repository;

import com.mymerit.mymerit.domain.entity.Lobby;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbyRepository extends MongoRepository<Lobby,String> {

}
