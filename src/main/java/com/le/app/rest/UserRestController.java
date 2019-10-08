package com.le.app.rest;

import com.le.app.dto.UserDto;
import com.le.app.model.User;
import com.le.app.service.UserService;
import com.le.app.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/profiles")
    public ResponseEntity<UserDto> getUserProfile() {
        User loginedUser = userService.getLoginedUser();

        if (loginedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto result = UserDto.fromUser(loginedUser);
        return ResponseEntity.ok(result);
    }


    @PostMapping(path = "/users/iv/{id}")
    public void addIrregularVerbToLearntByLoginedUser(@PathVariable Long id) {
        userService.addIrregularVerbToLearnt(id);
    }

    @DeleteMapping(path = "/users/iv/{id}")
    public void removeIrregularVerbFromLearntByLoginedUse(@PathVariable Long id) {
        userService.removeIrregularVerbFromLearnt(id);
    }


    @ExceptionHandler // it handles bad requests
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(Exception exception) {
        return new ValidationError(exception.getMessage());
    }
}
