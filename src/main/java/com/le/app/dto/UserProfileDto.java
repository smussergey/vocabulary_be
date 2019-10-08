package com.le.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.le.app.model.User;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
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

    public static UserProfileDto fromUser(User user) {
        UserProfileDto userDto = new UserProfileDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
