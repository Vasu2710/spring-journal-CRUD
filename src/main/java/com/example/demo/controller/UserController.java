package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.UserService;
import com.example.demo.entity.User;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("getAll")
    public List<User> getAll(){
        return userService.getAll();
    }
    @PostMapping("create")
    public ResponseEntity<User> create(@RequestBody User user){
        try{
            userService.saveEntry(user);
        }
        catch (Exception e){
            logger.error("Exception while creating user", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("update/{userName}")
    public ResponseEntity<User> update(@RequestBody User newUserEntry, @PathVariable String userName){
        //finding by username
        User oldUserEntry =  userService.findByUserName(userName);
        if (oldUserEntry!=null){
            oldUserEntry.setUserName(newUserEntry.getUserName());
            oldUserEntry.setPassword(newUserEntry.getPassword());
            userService.saveEntry(oldUserEntry);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
