package com.nk.agri.store.services.impl;

import com.nk.agri.store.dtos.PageableResponse;
import com.nk.agri.store.entities.Role;
import com.nk.agri.store.entities.User;
import com.nk.agri.store.dtos.UserDto;
import com.nk.agri.store.exceptions.ResourceNotFoundException;
import com.nk.agri.store.repositories.RoleRepository;
import com.nk.agri.store.repositories.UserRepository;
import com.nk.agri.store.services.UserService;
import com.nk.agri.store.utility.Helper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${normal.role.id}")
    private String normalRoleId;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //Encoding password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User entity = modelMapper.map(userDto, User.class);

        //Fetch role of normal and set it to user
        Role role = roleRepository.findById(normalRoleId).get();
        entity.getRoles().add(role);

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
        //Delete user profile image
        String fullPath = imageUploadPath + entity.getImageName();
        //From Java 17 we have this class
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            log.info("User image not found in the folder");
            throw new RuntimeException(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.delete(entity);
    }

    @Override
    public PageableResponse<UserDto> getAllUSer(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);
        return pageableResponse;
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

    @Override
    public Optional<User> findUserByEmailForGoogle(String email) {
        return userRepository.findByEmail(email);
    }
}
