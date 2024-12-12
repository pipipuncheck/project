package com.example.jwtauth.presentation.controller.auth;

import com.example.jwtauth.application.auth.service.AuthService;
import com.example.jwtauth.application.jwt.service.JwtService;
import com.example.jwtauth.domain.entity.User;
import com.example.jwtauth.presentation.controller.auth.dto.command.AuthCommand;
import com.example.jwtauth.presentation.controller.auth.dto.query.AuthQuery;
import com.example.jwtauth.presentation.controller.auth.dto.query.JwtQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }


    @PostMapping("/signup")
    public JwtQuery registration(@RequestBody AuthCommand command){
        User user = authService.register(command);
        return new JwtQuery(jwtService.generateToken(user));
    }

    @PostMapping("/signin")
    public JwtQuery login(@RequestBody AuthQuery authQuery){
        User user = authService.authenticate(authQuery.getUsername(), authQuery.getPassword());
        return new JwtQuery(jwtService.generateToken(user));
    }
}
