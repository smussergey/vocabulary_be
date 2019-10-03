package com.le.app.rest;


import com.le.app.dto.AuthenticationRequestDto;
import com.le.app.dto.UserDto;
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

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("userFirstName", user.getFirstName());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

    }

    @RequestMapping(path = "/register", method = {RequestMethod.POST,
            RequestMethod.PUT})
    // redo this method, hibernate makes additional query to get id? which we have
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto,
                                            Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        User resultUser = userService.register(userDto);

        String token = jwtTokenProvider.createToken(resultUser.getUsername(), resultUser.getRoles());

        Map<Object, Object> response = new HashMap<>();
        response.put("username", resultUser.getUsername());
        response.put("userFirstName", resultUser.getFirstName());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }


    @ExceptionHandler // it handles bad requests
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(Exception exception) {
        return new ValidationError(exception.getMessage());
    }

}
