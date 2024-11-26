package com.example.taskhive.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public String sendNotification(String message) {
        return message;
    }
}
