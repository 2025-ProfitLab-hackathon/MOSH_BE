package com.example.mosh_be.controller;

import com.example.mosh_be.dto.chatbot.ChatMessageRequest;
import com.example.mosh_be.dto.chatbot.ChatMessageResponse;
import com.example.mosh_be.service.ChatbotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chatbot")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @PostMapping("/messages")
    public ResponseEntity<ChatMessageResponse> sendMessage(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody ChatMessageRequest request
    ) {
        ChatMessageResponse response = chatbotService.sendMessage(userId, request);
        return ResponseEntity.ok(response);
    }
}
