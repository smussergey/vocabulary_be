package com.le.app.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.le.app.model.User;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
// Todo avoid code duplication with class UserRegisterDto
public class UserProfileDto {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    public static UserProfileDto fromUser(User user) {
        UserProfileDto userDto = new UserProfileDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
