package com.example.jwtauth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    private int id;
    private int userId;
    private int artistId;
    private int rating;
    private String comment;
}
