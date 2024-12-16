package com.example.jwtauth.presentation.controller.manager.dto;

import com.example.jwtauth.domain.entity.Artist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventQuery {

    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private Artist artist;

    private String country;
    private String city;
    private String locationName;
    private String address;

}
