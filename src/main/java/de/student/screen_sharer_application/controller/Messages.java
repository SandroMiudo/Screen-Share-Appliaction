package de.student.screen_sharer_application.controller;

import java.util.UUID;

public class Messages {

    public static class AdminMessage {
        @Override
        public String toString() {
            return "Adding a new admin";
        }
    }

    public record InviteMessage(UUID id) {

        @Override
        public String toString() {
            return String.format("Inviting the user with id=%s ",id.toString());
        }
    }

    public record RemoveMessage(String roomName) {

        @Override
        public String toString() {
            return String.format("Removing the user %s", roomName);
        }
    }

    public record FriendRemove(String username) {

        @Override
        public String toString() {
            return String.format("Removing friend %s",username);
        }
    }

    public record FriendAdd(String username){
        @Override
        public String toString() {
            return String.format("Adding friend %s",username);
        }
    }

}
