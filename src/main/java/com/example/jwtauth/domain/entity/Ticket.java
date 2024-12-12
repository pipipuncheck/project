package com.example.jwtauth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class Ticket {

    private Integer id;
    private String eventName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private LocalDate date;
    private Artist artist;
}
