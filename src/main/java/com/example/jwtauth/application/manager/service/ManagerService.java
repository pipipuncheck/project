package com.example.jwtauth.application.manager.service;

import com.example.jwtauth.domain.entity.Artist;
import com.example.jwtauth.domain.entity.Event;
import com.example.jwtauth.infrastructure.repository.UserRepository;
import com.example.jwtauth.presentation.controller.manager.dto.ArtistCommand;
import com.example.jwtauth.presentation.controller.manager.dto.EventCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private final UserRepository userRepository;

    @Autowired
    public ManagerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void createEvent(EventCommand eventCommand) {

        Artist artist = userRepository.findArtistByName(eventCommand.getArtistName()).orElseThrow(() ->
                new IllegalArgumentException("артиста с таким именем не найдено"));

        Event event = Event.builder()
                .name(eventCommand.getName())
                .artistId(artist.getId())
                .startTime(eventCommand.getStartTime())
                .endTime(eventCommand.getEndTime())
                .country(eventCommand.getCountry())
                .city(eventCommand.getCity())
                .locationName(eventCommand.getLocationName())
                .address(eventCommand.getAddress())
                .date(eventCommand.getDate())
                .build();
        userRepository.save(event);

    }

    public void createArtist(ArtistCommand artistCommand) {

        Artist artist = Artist.builder()
                .name(artistCommand.getName())
                .genre(artistCommand.getGenre())
                .description(artistCommand.getDescription())
                .build();

        userRepository.save(artist);


    }

    public List<String> showAllArtists() {
        return userRepository.showAllArtists()
                .stream()
                .map(Artist::getName)
                .collect(Collectors.toList());
    }
}
