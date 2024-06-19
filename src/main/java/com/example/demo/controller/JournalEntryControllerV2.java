package com.example.demo.controller;

import com.example.demo.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.JournalEntry;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("getAll")
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }

    @GetMapping("getById/{id}")
    public JournalEntry getbyid(@PathVariable ObjectId id){
        return journalEntryService.getById(id).orElse(null);
    }
    @PostMapping("create")
    public JournalEntry create(@RequestBody JournalEntry entry){
        entry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(entry);
        return entry;
    }
    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable ObjectId id){
        journalEntryService.deleteEntry(id);
        return true;
    }
    @PutMapping("update/{id}")
    public JournalEntry update(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry = journalEntryService.getById(id).orElse(null);
        if (oldEntry != null){
            oldEntry.setContent(newEntry.getContent()!= null && !newEntry.getContent().
                    equals("")? newEntry.getContent(): oldEntry.getContent());
            oldEntry.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().
                    equals("")? newEntry.getTitle(): oldEntry.getTitle());
        }
        journalEntryService.saveEntry(oldEntry);
        return oldEntry;
    }


}