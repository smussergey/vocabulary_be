package com.le.app.rest;


import com.le.app.model.dto.AuthenticationRequestDto;
import com.le.app.model.dto.UserRegisterDto;
import com.le.app.model.User;
import com.le.app.security.jwt.JwtTokenProvider;
import com.le.app.service.UserService;
import com.le.app.validation.ValidationError;
import com.le.app.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationRestController {
    @Autowired
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthenticationRequestDto requestDto, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsernameWithoutRoles(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }
            Map<Object, Object> response = createResponse(user);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

    }


    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto userRegisterDto,
                                      Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        User registeredUser = userService.register(userRegisterDto);

        Map<Object, Object> response = createResponse(registeredUser);
        return ResponseEntity.ok(response);
    }

    private Map<Object, Object> createResponse(User user) {
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

        Map<Object, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("userFirstName", user.getFirstName());
        response.put("token", token);

        return response;
    }


    @ExceptionHandler // it handles bad requests
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleValidationExceptions(Exception exception) {
        return new ValidationError(exception.getMessage());
    }

}
