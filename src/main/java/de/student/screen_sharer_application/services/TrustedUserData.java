package de.student.screen_sharer_application.services;

import java.util.UUID;

public record TrustedUserData(String private_Key, UUID user_ID,UUID session_ID) {}
