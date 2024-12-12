package com.example.jwtauth.presentation.controller.auth.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthCommand {

    private String username;

    private String email;

    private String password;
}
