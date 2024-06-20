package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    //dependency injection , we are injecting repository in service
    @Autowired
    private UserRepository userRepository;

    //implementation of the interface
    public void saveEntry(User user){
        userRepository.save(user);
    }
    public List<User>getAll(){
        return userRepository.findAll();
    }
    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteEntry(ObjectId id){
        userRepository.deleteById(id);
    }
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);

    }

    public List<User> findbyJournalEntryId(ObjectId journal_id) {
        return userRepository.findByJournalEntriesContaining(journal_id);
    }


}
