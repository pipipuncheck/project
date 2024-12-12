package com.example.jwtauth.application.auth.service;

import com.example.jwtauth.domain.entity.User;
import com.example.jwtauth.domain.enums.Role;
import com.example.jwtauth.infrastructure.repository.UserRepository;
import com.example.jwtauth.presentation.controller.auth.dto.command.AuthCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String username, String password){
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("Неверный логин"));
        if (passwordEncoder.matches(password, user.getPassword()))
            return user;

        throw new IllegalArgumentException("Неверный пароль");
    }

    public User register(AuthCommand command){
        Optional<User> userDB = userRepository.findByUsername(command.getUsername());

        if(userDB.isPresent())
            throw new IllegalArgumentException("пользователь с таким логином уже существует");

        User user = User.builder()
                .username(command.getUsername())
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return user;
    }

}
