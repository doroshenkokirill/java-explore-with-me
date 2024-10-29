package ru.practicum.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewUserRequest {

    @NotBlank(message = "Field name must not be blank")
    @Size(min = 2, max = 250, message = "Size of field email must be [2;250]")
    private String name;

    @NotBlank(message = "Field email must not be blank")
    @Size(min = 6, max = 254, message = "Size of field email must be [6;254]")
    @Email(message = "Field email must be valid")
    private String email;
}
