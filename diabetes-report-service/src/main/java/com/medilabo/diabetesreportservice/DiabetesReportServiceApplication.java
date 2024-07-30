package com.medilabo.diabetesreportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DiabetesReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiabetesReportServiceApplication.class, args);
    }

}
