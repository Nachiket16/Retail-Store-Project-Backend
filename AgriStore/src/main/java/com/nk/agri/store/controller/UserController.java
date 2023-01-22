package com.nk.agri.store.controller;

import com.nk.agri.store.dtos.UserDto;
import com.nk.agri.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //create
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    //update

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId,
            @RequestBody UserDto userDto
    ){
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("userId") String userId
    ){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User is deleted Successfully", HttpStatus.OK);
    }

    //get all
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUSer(), HttpStatus.OK);
    }

    //get single
    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserById(
            @PathVariable("userId") String userId
    ){
        UserDto userById = userService.getUserById(userId);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
    //get by email
    @GetMapping("/email/{userId}")
    public ResponseEntity<UserDto> getUserByEmail(
            @PathVariable String email
    ){
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    //search user
    @GetMapping("/email/{userId}")
    public ResponseEntity<List<UserDto>> searchUser(
            @PathVariable String keyword
    ){
        return new ResponseEntity<>(userService.searUser(keyword), HttpStatus.OK);
    }
}
