package de.student.screen_sharer_application.services;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public record LeavingRoomData(UUID roomID, UUID userID) {
}
