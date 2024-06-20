package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.JournalEntryService;
import com.example.demo.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.JournalEntry;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all = journalEntryService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<JournalEntry> getbyid(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("getByUsername/{userName}")
    public ResponseEntity<?> getAllJournalEntriesofUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> journal_entries = user.getJournalEntries();
        return new ResponseEntity<>(journal_entries, HttpStatus.OK);
    }

    @PostMapping("create/{username}")
    public ResponseEntity<JournalEntry>create(@RequestBody JournalEntry entry, @PathVariable String username){
        try {
            journalEntryService.saveEntry(entry, username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id){
        journalEntryService.deleteEntry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("update/{username}/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId id, @PathVariable String userName, @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry = journalEntryService.getById(id).orElse(null);
        if (oldEntry != null) {
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().
                    equals("") ? newEntry.getContent() : oldEntry.getContent());
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().
                    equals("") ? newEntry.getTitle() : oldEntry.getTitle());

            journalEntryService.save(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}