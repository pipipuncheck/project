package com.example.jwtauth.application.user.mapper;

import com.example.jwtauth.domain.entity.Artist;
import com.example.jwtauth.domain.entity.Event;
import com.example.jwtauth.domain.entity.Ticket;
import com.example.jwtauth.presentation.controller.user.dto.query.TicketQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    private final ModelMapper mapper;

    @Autowired
    public TicketMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public TicketQuery convertToTicketQuery(Ticket ticket){
        return mapper.map(ticket, TicketQuery.class);
    }

    public Ticket convertEventToTicket(Event event, int ticketId, Artist artist){
        return Ticket.builder()
                .id(ticketId)
                .eventName(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .location(event.getLocation())
                .date(event.getDate())
                .artist(artist)
                .build();
    }
}
