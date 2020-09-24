package com.sdn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SdnMonitoringToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(SdnMonitoringToolApplication.class, args);
    }
}
