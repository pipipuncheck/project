package com.example.jwtauth.infrastructure.repository;

import com.example.jwtauth.domain.entity.BackUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class BackUpRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BackUpRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void backUp(BackUp backUp){
        String sql = "insert into backups(backup_name, backup_time, status, details) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql,backUp.getBackupName(), backUp.getBackupTime(), backUp.getStatus(), backUp.getDetails());
    }
}
