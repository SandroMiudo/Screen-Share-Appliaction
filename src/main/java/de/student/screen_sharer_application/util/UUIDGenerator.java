package de.student.screen_sharer_application.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator {

    public UUID generate(){
        return UUID.randomUUID();
    }
}
