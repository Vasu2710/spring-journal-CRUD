package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.JournalEntryService;
import com.example.demo.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.JournalEntry;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<JournalEntry> getbyid(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//
//    @GetMapping("getByUsername/{userName}")
//    public ResponseEntity<?> getAllJournalEntriesofUser(@PathVariable String userName){
//        User user = userService.findByUserName(userName);
//        List<JournalEntry> journal_entries = user.getJournalEntries();
//        return new ResponseEntity<>(journal_entries, HttpStatus.OK);
//    }

    @PostMapping("create/")
    public ResponseEntity<JournalEntry>create(@RequestBody JournalEntry entry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(entry, userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean check =journalEntryService.deleteEntry(id, userName);
        if(check)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        try {
            if (user.getJournalEntries().contains(id)) {
                JournalEntry oldEntry = journalEntryService.getById(id).orElse(null);
                if (oldEntry != null) {
                    oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().
                            equals("") ? newEntry.getContent() : oldEntry.getContent());
                    oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().
                            equals("") ? newEntry.getTitle() : oldEntry.getTitle());
                }
                journalEntryService.save(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while updating!", e);
            }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}