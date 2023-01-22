package com.nk.agri.store.services;

import com.nk.agri.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    //Create
    UserDto createUser(UserDto user);

    //update
    UserDto updateUser(UserDto user, String userId);
    //delete
    void deleteUser(String userId);
    //getAll user
    List<UserDto> getAllUSer();
    //get single by id
    UserDto getUserById(String userID);
    //get by email
    UserDto getUserByEmail(String email);
    //search user
    List<UserDto> searUser(String keyword);
    //other user specific features

}
