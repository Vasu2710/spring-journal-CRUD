package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping("getAll")
    public List<User> getAll() {
        return userService.getAll();
    }



    @PutMapping("update")
    public ResponseEntity<User> update(@RequestBody User newUserEntry){
        //finding by username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User oldUserEntry =  userService.findByUserName(userName);
        if (oldUserEntry!=null){
            oldUserEntry.setUserName(newUserEntry.getUserName());
            oldUserEntry.setPassword(newUserEntry.getPassword());
            userService.saveUser(oldUserEntry);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByuserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
