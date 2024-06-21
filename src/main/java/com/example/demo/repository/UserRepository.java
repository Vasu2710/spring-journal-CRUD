package com.example.demo.repository;

import com.example.demo.entity.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, ObjectId>{

    User findByUserName(String username);
    List<User> findByJournalEntriesContaining(ObjectId id);
    void deleteByuserName(String username);

}
