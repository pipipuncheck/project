package com.example.jwtauth.application.user.service;

import com.example.jwtauth.application.admin.mapper.UserMapper;
import com.example.jwtauth.application.manager.mapper.EventMapper;
import com.example.jwtauth.application.user.mapper.ArtistMapper;
import com.example.jwtauth.application.user.mapper.TicketMapper;
import com.example.jwtauth.domain.entity.Artist;
import com.example.jwtauth.domain.entity.Event;
import com.example.jwtauth.domain.entity.Review;
import com.example.jwtauth.domain.entity.User;
import com.example.jwtauth.infrastructure.repository.UserRepository;
import com.example.jwtauth.presentation.controller.admin.dto.query.UserQuery;
import com.example.jwtauth.presentation.controller.manager.dto.ArtistQuery;
import com.example.jwtauth.presentation.controller.manager.dto.EventQuery;
import com.example.jwtauth.presentation.controller.user.dto.command.ReviewCommand;
import com.example.jwtauth.presentation.controller.user.dto.query.TicketQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TicketMapper ticketMapper;
    private final EventMapper eventMapper;
    private final ArtistMapper artistMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, TicketMapper ticketMapper, EventMapper eventMapper, ArtistMapper artistMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.ticketMapper = ticketMapper;
        this.eventMapper = eventMapper;
        this.artistMapper = artistMapper;
    }

    public UserQuery checkMyInfo(UserDetails userDetails){
        return userMapper.convertToUserQuery((User) userDetails);
    }

    public List<TicketQuery> checkMyTickets(UserDetails userDetails){
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("пользователь с таким username не найден"));

        return userRepository.showAllTicketsByUsername(user.getId())
                .stream()
                .filter(userRepository::isTicketOrEventActive)
                .map(ticketMapper::convertToTicketQuery)
                .collect(Collectors.toList());
    }

    public List<TicketQuery> checkMyExpiredTickets(UserDetails userDetails){
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("пользователь с таким username не найден"));

        return userRepository.showAllTicketsByUsername(user.getId())
                .stream()
                .filter(ticket -> !userRepository.isTicketOrEventActive(ticket))
                .map(ticketMapper::convertToTicketQuery)
                .collect(Collectors.toList());
    }



    public List<EventQuery> checkAllEventsByDate(LocalDate date){
        return userRepository.checkEventsByDate(date)
                .stream()
                .map(event -> {
                    Artist artist = userRepository.findArtistById(event.getArtistId()).orElseThrow(() ->
                            new IllegalArgumentException("такого артиста нет"));
                    EventQuery eventQuery = eventMapper.convertToEventQuery(event);
                    eventQuery.setArtist(artist);
                    return eventQuery;
                })
                .collect(Collectors.toList());
    }

    public void buyTicket(UserDetails userDetails, String name) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("пользователь с таким логином не найден"));

        Event event = userRepository.findEventByName(name).orElseThrow(() ->
                new IllegalArgumentException("мероприятия с таким названием не найдено"));

        if(userRepository.isTicketOrEventActive(event))
            userRepository.makeTicket(user.getId(), event.getId());
        else throw new IllegalArgumentException("невозможно купить билет на уже прошедшее мероприятие");

    }

    public EventQuery checkEventInfo(String name){
        Event event = userRepository.findEventByName(name).orElseThrow(() ->
                new IllegalArgumentException("мероприятия с таким названием не найдено"));
        EventQuery eventQuery = eventMapper.convertToEventQuery(event);
        Artist artist = userRepository.findArtistById(event.getArtistId()).orElseThrow(() ->
                new IllegalArgumentException("арист с таким id не найден"));

        eventQuery.setArtist(artist);

        return eventQuery;
    }

    public ArtistQuery checkArtistInfo(int artistId) {
        Artist artist = userRepository.findArtistById(artistId).orElseThrow(() ->
                new IllegalArgumentException("такого артист не найден"));
        return artistMapper.convertToArtistQuery(artist);
    }

    public void addReview(UserDetails userDetails, int artistId, ReviewCommand command) {

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("такого пользователя нет"));

        Review review = Review.builder()
                .userId(user.getId())
                .artistId(artistId)
                .rating(command.getRating())
                .comment(command.getComment())
                .build();


        userRepository.save(review);
    }
}
