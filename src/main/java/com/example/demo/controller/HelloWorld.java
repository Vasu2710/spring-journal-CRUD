package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.JournalEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class HelloWorld {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("getAll")
    public ArrayList<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("getById/{id}")
    public JournalEntry getbyid(@PathVariable Long id){
        return journalEntries.get(id);
    }
    @PostMapping("create")
    public JournalEntry create(@RequestBody JournalEntry entry){
        journalEntries.put(entry.getId(), entry);
        return entry;
    }
    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable Long id){
        journalEntries.remove(id);
        return true;
    }
    @PutMapping("update/{id}")
    public JournalEntry update(@PathVariable Long id, @RequestBody JournalEntry entry){
        journalEntries.put(id, entry);
        return entry;
    }


}
