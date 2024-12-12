package com.example.jwtauth.presentation.controller.auth.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtQuery {
    private String accessToken;
}
