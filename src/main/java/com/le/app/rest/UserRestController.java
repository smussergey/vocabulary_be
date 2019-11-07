package com.le.app.rest;

import com.le.app.model.dto.UserProfileDto;
import com.le.app.service.UserService;
import com.le.app.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
        userService.updateUserProfile(userProfileDto);

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

}
