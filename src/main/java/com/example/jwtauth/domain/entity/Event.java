package com.example.jwtauth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
public class Event {

    private Integer id;
    private String name;
    private int artistId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;

    private String country;
    private String city;
    private String locationName;
    private String address;
}
