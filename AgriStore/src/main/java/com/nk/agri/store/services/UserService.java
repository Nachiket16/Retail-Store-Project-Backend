package com.nk.agri.store.services;

import com.nk.agri.store.dtos.PageableResponse;
import com.nk.agri.store.dtos.UserDto;
import com.nk.agri.store.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    //Create
    UserDto createUser(UserDto user);

    //update
    UserDto updateUser(UserDto user, String userId);
    //delete
    void deleteUser(String userId) throws IOException;
    //getAll user
    PageableResponse<UserDto> getAllUSer(int pageNumber, int pageSize, String sortBy, String sortDir);
    //get single by id
    UserDto getUserById(String userID);
    //get by email
    UserDto getUserByEmail(String email);
    //search user
    List<UserDto> searUser(String keyword);
    //other user specific features

    Optional<User> findUserByEmailForGoogle(String email);

}
