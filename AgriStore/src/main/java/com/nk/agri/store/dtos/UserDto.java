package com.nk.agri.store.dtos;

import com.nk.agri.store.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;
    @Size(min = 3, message = "Name should be greater than 3 char")
    private String name;
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid User Email !!")
    @NotBlank(message = "Email is required !!")
    private String email;
    @NotBlank(message = "Password required")
    private String password;
    @Size(min = 4, max = 6)
    private String gender;
    @NotBlank
    private String about;
    @ImageNameValid
    private String imageName;

}
