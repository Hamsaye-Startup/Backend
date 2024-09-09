package com.hamsaye.chat.users.models;

/**
 * Represents the connection status of a user in the chat application.
 * <p>
 * This enum is used to indicate whether a user is currently connected or disconnected
 * from the WebSocket chat system.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public enum ConnectionStatus {

    /**
     * Indicates that the user is connected to the WebSocket chat system.
     */
    CONNECTED,

    /**
     * Indicates that the user is disconnected from the WebSocket chat system.
     */
    DISCONNECTED
}
