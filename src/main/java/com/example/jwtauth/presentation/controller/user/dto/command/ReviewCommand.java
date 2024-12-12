package com.example.jwtauth.presentation.controller.user.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCommand {

    private int rating;
    private String comment;
}
