package com.nk.agri.store.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String categoryId;
    @NotBlank
    @Min(value = 4, message = "title must be minimum of the 4 character !!!")
    private String title;
    @NotBlank(message = "Description required")
    private String description;
    private String coverImage;
}
