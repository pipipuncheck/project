package com.example.jwtauth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
public class BackUp {

    private String backupName;
    private LocalDateTime backupTime;
    private String status;
    private String details;
}
