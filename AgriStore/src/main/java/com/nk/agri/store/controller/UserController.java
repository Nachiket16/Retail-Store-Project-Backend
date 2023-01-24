package com.nk.agri.store.controller;

import com.nk.agri.store.dtos.ApiResponseMsg;
import com.nk.agri.store.dtos.UserDto;
import com.nk.agri.store.services.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
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
    public ResponseEntity<ApiResponseMsg> deleteUser(
            @PathVariable("userId") String userId
    ){
        userService.deleteUser(userId);
        ApiResponseMsg msg = ApiResponseMsg
                .builder()
                .msg("User is deleted Successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(msg, HttpStatus.OK);
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
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(
            @PathVariable("keyword") String keyword
    ){
        return new ResponseEntity<>(userService.searUser(keyword), HttpStatus.OK);
    }
}
