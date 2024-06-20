package com.example.demo.service;

import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.User;
import com.example.demo.repository.JournalEntryRepository;
import com.example.demo.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    //dependency injection , we are injecting repository in service
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    //implementation of the interface
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            journalEntry.setDate(LocalDateTime.now());
            User user = userService.findByUserName(userName);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }
        catch(Exception e){
            System.out.println(e);
            //By not re-throwing the exception, the transaction will not be marked for rollback automatically
            throw new RuntimeException("An error occured!");

        }
    }
    public void save(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry>getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteEntry(ObjectId id){
         journalEntryRepository.deleteById(id);
         List<User> users= userService.findbyJournalEntryId(id);
         for(User user :  users){
            user.getJournalEntries().remove(id);
            userService.saveEntry(user);
         }


    }

}
