package com.nk.agri.store.services.impl;

import com.nk.agri.store.controller.User;
import com.nk.agri.store.dtos.UserDto;
import com.nk.agri.store.repositories.UserRepository;
import com.nk.agri.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto user) {
        User entity = modelMapper.map(user, User.class);
        User newEntity = userRepository.save(entity);
        return modelMapper.map(newEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User entity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with given Id"));
        entity.setName(userDto.getName());
        entity.setEmail(userDto.getEmail());
        entity.setAbout(userDto.getAbout());
        entity.setGender(userDto.getGender());
        entity.setAbout(userDto.getAbout());
        entity.setImageName(userDto.getImageName());
        User newEntity = userRepository.save(entity);
        return modelMapper.map(newEntity, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        User entity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with id"));
        userRepository.delete(entity);
    }

    @Override
    public List<UserDto> getAllUSer() {
        List<User> allEntity = userRepository.findAll();
        List<UserDto> userDtoList = allEntity.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto getUserById(String userID) {
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found with id"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with the given Email id"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }
}
