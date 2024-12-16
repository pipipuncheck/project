package com.example.jwtauth.application.backup.service;

import com.example.jwtauth.domain.entity.BackUp;
import com.example.jwtauth.infrastructure.repository.BackUpRepository;
import com.example.jwtauth.infrastructure.util.backup.DataBaseBackUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BackUpService {

    private final BackUpRepository backUpRepository;
    private final DataBaseBackUp dataBaseBackUp;

    @Value("${spring.datasource.username}")
    private String username; // имя пользователя для бд
    @Value("${backup.db.name}")
    private String dbName; // название бд
    private String backupName = String.format("db_backup_%s.dump", LocalDate.now()); // название файла
    @Value("${backup.file.path}")
    private String filePath; // путь для сохранения файла
    private String status;
    private String details;

    @Autowired
    public BackUpService(BackUpRepository backUpRepository, DataBaseBackUp dataBaseBackUp) {
        this.backUpRepository = backUpRepository;
        this.dataBaseBackUp = dataBaseBackUp;
    }

    public void backUp(UserDetails userDetails){
        int result = dataBaseBackUp.backupDatabase(username, dbName, filePath);

        if(result == 0) {
            status = "Successfully";
            details = String.format("Backup was made by %s", userDetails.getUsername());
        }
        else {
            status = "Failed";
            details = ":(";
        }

        BackUp backUp = BackUp.builder()
                .backupName(backupName)
                .backupTime(LocalDateTime.now())
                .status(status)
                .details(details)
                .build();

        backUpRepository.backUp(backUp);
    }
}
