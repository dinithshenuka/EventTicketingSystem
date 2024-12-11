package lk.ac.iit.EventTicketingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        return message;
    }

    @GetMapping("/sendTestMessage")
    public String sendTestMessage() {
        messagingTemplate.convertAndSend("/topic/messages", "{\"type\":\"TICKET_UPDATE\"}");
        return "Test message sent";
    }
}