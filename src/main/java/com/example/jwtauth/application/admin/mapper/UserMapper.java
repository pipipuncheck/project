package com.example.jwtauth.application.admin.mapper;

import com.example.jwtauth.domain.entity.User;
import com.example.jwtauth.presentation.controller.admin.dto.query.UserQuery;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper mapper;

    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UserQuery convertToUserQuery(User user){
        return mapper.map(user, UserQuery.class);
    }

    public User convertToUser(UserQuery userQuery){
        return mapper.map(userQuery, User.class);
    }

}
