package com.armaan.apiportal.config; // Match your package structure

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
public class DatabaseKeepAlive {

    @Autowired
    private DataSource dataSource;

    // 10,800,000 milliseconds = 3 Hours
    @Scheduled(fixedRate = 10800000)
    public void pingAivenDatabase() {
        System.out.println("Executing automated keep-alive query to protect Aiven Free Tier...");
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT 1")) {
            
            statement.executeQuery();
            System.out.println("Keep-alive ping successful. Database state: ACTIVE.");
            
        } catch (Exception e) {
            System.err.println("Keep-alive ping failed: " + e.getMessage());
        }
    }
}
