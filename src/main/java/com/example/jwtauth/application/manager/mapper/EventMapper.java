package com.example.jwtauth.application.manager.mapper;

import com.example.jwtauth.domain.entity.Event;
import com.example.jwtauth.presentation.controller.manager.dto.EventQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    private final ModelMapper mapper;

    @Autowired
    public EventMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public EventQuery convertToEventQuery(Event event){
        return mapper.map(event, EventQuery.class);
    }
}
