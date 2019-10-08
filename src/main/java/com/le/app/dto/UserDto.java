package com.le.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import com.le.app.model.User;

import javax.validation.constraints.NotBlank;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    private String lastName;
    //@Email(message = "Email should be valid") ToDo this should be uncommitted
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
