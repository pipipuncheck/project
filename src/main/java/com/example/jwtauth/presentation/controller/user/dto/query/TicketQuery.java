package com.example.jwtauth.presentation.controller.user.dto.query;

import com.example.jwtauth.domain.entity.Artist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketQuery {

    private String eventName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private LocalDate date;
    private Artist artist;
}
