package com.le.app.model.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
