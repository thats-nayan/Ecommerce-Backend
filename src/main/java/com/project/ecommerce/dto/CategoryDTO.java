package com.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {
    private Long categoryId;

    @NotBlank(message = "Category name cannot be blank")
    @Size(min = 5, max = 32, message = "Category name must be between 5 and 32 characters")
    private String categoryName;
}
