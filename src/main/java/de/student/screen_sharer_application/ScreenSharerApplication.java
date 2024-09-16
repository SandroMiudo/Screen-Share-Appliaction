package de.student.screen_sharer_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScreenSharerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScreenSharerApplication.class, args);
    }

}
