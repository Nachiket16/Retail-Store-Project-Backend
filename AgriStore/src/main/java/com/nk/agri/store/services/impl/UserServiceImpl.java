package com.nk.agri.store.services.impl;

import com.nk.agri.store.entities.User;
import com.nk.agri.store.dtos.UserDto;
import com.nk.agri.store.exceptions.ResourceNotFoundException;
import com.nk.agri.store.repositories.UserRepository;
import com.nk.agri.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User entity = modelMapper.map(userDto, User.class);
        User newEntity = userRepository.save(entity);
        return modelMapper.map(newEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User entity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given Id"));
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
        User entity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with id"));
        userRepository.delete(entity);
    }

    @Override
    public List<UserDto> getAllUSer(int pageNumber, int pageSize) {

        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        Page<User> page = userRepository.findAll(pageable);
        List<User> users = page.getContent();
        List<UserDto> userDtoList = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with given id !!"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email id !!"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }
}
