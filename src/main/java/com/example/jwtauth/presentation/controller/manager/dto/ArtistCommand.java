package com.example.jwtauth.presentation.controller.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistCommand {

    private String name;
    private String genre;
    private String description;
}
