package ru.practicum.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewCategoryDto {

    @NotBlank(message = "Category name must not be blank")
    @Size(min = 1, max = 50, message = "Size of field name must be [1;50]")
    private String name;
}
