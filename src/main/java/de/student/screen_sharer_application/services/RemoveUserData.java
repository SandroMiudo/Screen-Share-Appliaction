package de.student.screen_sharer_application.services;

import java.util.UUID;

public record RemoveUserData(UUID removeUserID, UUID adminID, UUID roomID) {}
