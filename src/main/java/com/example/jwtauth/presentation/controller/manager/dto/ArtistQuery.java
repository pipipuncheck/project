package com.example.jwtauth.presentation.controller.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistQuery {

    private int id;
    private String name;
    private String genre;
    private Integer rating;
    private String description;
}
