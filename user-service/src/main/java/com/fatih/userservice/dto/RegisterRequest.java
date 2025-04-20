package com.fatih.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.fatih.userservice.exception.ExceptionMessages.passwordLengthException;
import static com.fatih.userservice.exception.ExceptionMessages.usernameBlankException;

@Data
public class RegisterRequest {
    @NotBlank(message = usernameBlankException)
    private String username;

    @Size(min = 8, message = passwordLengthException)
    private String password;
}
