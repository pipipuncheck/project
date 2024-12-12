package com.example.jwtauth.application.user.mapper;

import com.example.jwtauth.domain.entity.Artist;
import com.example.jwtauth.presentation.controller.manager.dto.ArtistQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ArtistMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ArtistQuery convertToArtistQuery(Artist artist){
        return modelMapper.map(artist, ArtistQuery.class);
    }
}
