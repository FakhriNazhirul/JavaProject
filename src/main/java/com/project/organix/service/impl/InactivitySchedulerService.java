package com.project.organix.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InactivitySchedulerService {

    @Scheduled(fixedRate = 86400000) // runs every 24 hours
    public void cleanUpInactiveUsers() {
        LocalDateTime threshold = LocalDateTime.now().minusMonths(6);
        // In a real app, this would query and deactivate inactive users
        System.out.println("[Scheduler] Checking for inactive users since: " + threshold);
    }

    @Scheduled(cron = "0 0 0 * * ?") // runs daily at midnight
    public void dailyPointReset() {
        System.out.println("[Scheduler] Running daily point reset at: " + LocalDateTime.now());
    }
}