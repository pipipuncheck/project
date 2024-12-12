package com.example.jwtauth.presentation.controller.admin.dto.query;

import com.example.jwtauth.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {

    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
}
