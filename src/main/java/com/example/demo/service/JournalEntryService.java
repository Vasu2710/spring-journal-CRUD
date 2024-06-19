package com.example.demo.service;

import com.example.demo.entity.JournalEntry;
import com.example.demo.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    //dependency injection , we are injecting repository in service
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    //implementation of the interface
    public void saveEntry(JournalEntry journalEntry){
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
    }

}
