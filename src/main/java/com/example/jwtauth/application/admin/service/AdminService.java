package com.example.jwtauth.application.admin.service;

import com.example.jwtauth.application.admin.mapper.UserMapper;
import com.example.jwtauth.infrastructure.repository.UserRepository;
import com.example.jwtauth.presentation.controller.admin.dto.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Autowired
    public AdminService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    public List<UserQuery> showAllUsers(){
        return userRepository.findAll()
                .stream().map(mapper::convertToUserQuery)
                .collect(Collectors.toList());
    }

    public UserQuery showUserById(Integer id){
        return mapper.convertToUserQuery(userRepository.findOneById(id).orElseThrow(() ->
                new NoSuchElementException("пользователь с таким id не найден")));
    }

    public void makeUserAdmin(int userId){
        userRepository.makeUserAdmin(userId);
    }

    public void makeUserManager(int userId){
        userRepository.makeUserManager(userId);
    }

    public void deleteUser(int userId){
        userRepository.deleteById(userId);
    }

}
