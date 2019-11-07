package com.le.app.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import com.le.app.model.User;
import javax.validation.constraints.NotBlank;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterDto extends UserProfileDto{
//    private Long id;
//    @NotBlank(message = "Username cannot be blank")
//    private String username;
//    @NotBlank(message = "First name cannot be blank")
//    private String firstName;
//    private String lastName;
//    @Email(message = "Email should be valid")
//    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    public User toUser() {
        User user = new User();
//        user.setId(id);
        user.setUsername(super.getUsername());
        user.setFirstName(super.getFirstName());
        user.setLastName(super.getLastName());
        user.setEmail(super.getEmail());
        user.setPassword(password);
        return user;
    }

}
