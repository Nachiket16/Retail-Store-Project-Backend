package com.nk.agri.store.controller;

import com.nk.agri.store.dtos.ApiResponseMsg;
import com.nk.agri.store.dtos.ImageResponse;
import com.nk.agri.store.dtos.PageableResponse;
import com.nk.agri.store.dtos.UserDto;
import com.nk.agri.store.services.FileService;
import com.nk.agri.store.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    //create
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }
    //update

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId,
            @RequestBody UserDto userDto
    ) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMsg> deleteUser(
            @PathVariable("userId") String userId
    ) throws IOException {
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
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<>(userService.getAllUSer(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    //get single
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable("userId") String userId
    ) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(
            @PathVariable("email") String email
    ) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    //search user
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(
            @PathVariable("keyword") String keyword
    ) {
        return new ResponseEntity<>(userService.searUser(keyword), HttpStatus.OK);
    }

    //Upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(
            @PathVariable("userId") String userId,
            @RequestParam("userImage") MultipartFile image
    ) throws IOException {
        String imageName = fileService.uploadImage(image, imageUploadPath);
        UserDto userDto = userService.getUserById(userId);
        userDto.setImageName(imageName);
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(imageName)
                .msg("Image is Successfully created !!!")
                .status(HttpStatus.CREATED)
                .success(true)
                .build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    //Serve User Image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        //
        UserDto userDto = userService.getUserById(userId);
        log.info("### User Image name {} ", userDto.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, userDto.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
