package com.example.jwtauth.presentation.controller.auth.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthQuery {

    private String username;
    private String password;
}
