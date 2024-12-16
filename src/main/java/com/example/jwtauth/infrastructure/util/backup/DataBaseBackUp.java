package com.example.jwtauth.infrastructure.util.backup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class DataBaseBackUp {

    @Value("${backup.pg_dump.path}")
    private String pgDumpPath;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${backup.db.port}")
    private String dbPort;

    public int backupDatabase(String username, String dbName, String outputPath) {
        String backupCommand = String.format(
                "%s -U %s -d %s -p %s -F c -f %s",
                pgDumpPath, username, dbName, dbPort, outputPath
        );

        ProcessBuilder processBuilder = new ProcessBuilder(backupCommand.split(" "));
        processBuilder.environment().put("PGPASSWORD", dbPassword); // тут пароль от базы данных

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println(line);
                    }
                }
            }
            return exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
