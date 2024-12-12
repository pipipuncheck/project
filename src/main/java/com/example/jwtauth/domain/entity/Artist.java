package com.example.jwtauth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
public class Artist {

    private int id;
    private String name;
    private String genre;
    private Integer rating;
    private String description;
}
