package com.example.jwtauth.presentation.controller.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCommand {

    private String name;
    private String artistName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private LocalDate date;
    private String country;
    private String city;
    private String locationName;
    private String address;
}
