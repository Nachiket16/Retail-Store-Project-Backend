package com.nk.agri.store.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Email
    private String email;
    @NotBlank(message = "Password required")
    private String password;
    @Size(min = 4, max = 6)
    private String gender;
    @NotBlank
    private String about;
    private String imageName;

}
