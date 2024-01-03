package com.oracle.projectGo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class ProjectGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectGoApplication.class, args);
    }

}
