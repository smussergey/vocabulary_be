package com.le.app.rest;

import com.le.app.model.dto.UserProfileDto;
import com.le.app.model.dto.UserRegisterDto;
import com.le.app.model.User;
import com.le.app.repository.UserWithSuchUsernameExistsException;
import com.le.app.service.UserService;
import com.le.app.validation.ValidationError;
import com.le.app.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/profile")
    public ResponseEntity<UserProfileDto> getUserProfile() {
        UserProfileDto result = userService.getUserProfile();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/users/profile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UserProfileDto userProfileDto,
                                               Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        try {
            userService.updateUserProfile(userProfileDto);
        } catch (Exception e) {
            List<String> exceptions = new ArrayList<>();

            return ResponseEntity.badRequest() // Todo check this
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("User profile was updated");
    }


    @PostMapping(path = "/users/iv/{id}")
    public void addIrregularVerbToLearntByLoginedUser(@PathVariable Long id) {
        userService.addIrregularVerbToLearnt(id);
    }

    @DeleteMapping(path = "/users/iv/{id}")
    public void removeIrregularVerbFromLearntByLoginedUse(@PathVariable Long id) {
        userService.removeIrregularVerbFromLearnt(id);
    }

//    @ExceptionHandler(UserWithSuchUsernameExistsException.class)
//    public ResponseEntity<?> UserWithSuchUsernameExistsException(UserWithSuchUsernameExistsException exc) {
//        return ResponseEntity.unprocessableEntity().build();
//    }
//
//    @ExceptionHandler // it handles bad requests
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ValidationError handleException(Exception exception) {
//        return new ValidationError(exception.getMessage());
//    }


}
