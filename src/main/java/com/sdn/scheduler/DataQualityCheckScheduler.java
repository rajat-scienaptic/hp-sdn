package com.sdn.scheduler;

import com.sdn.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataQualityCheckScheduler {
    @Autowired
    protected EmailService emailService;

    @Scheduled(cron = "0 0 1 * * MON")
    public void sendEmail(){
        emailService.sendEmail();
    }
}
