package de.student.screen_sharer_application.services;

import java.util.UUID;

public record AdminChangeData(UUID roomID, UUID newAdmin, UUID currentAdmin) {}
