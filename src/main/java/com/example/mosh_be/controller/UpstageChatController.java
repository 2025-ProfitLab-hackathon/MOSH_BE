package com.example.mosh_be.controller;

import com.example.mosh_be.dto.chatbot.ChatMessageResponse;
import com.example.mosh_be.dto.request.UpstageChatRequest;
import com.example.mosh_be.service.UpstageChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UpstageChatController {

    private final UpstageChatService upstageChatService;

    @PostMapping("/chatbot/messages")
    public ResponseEntity<ChatMessageResponse> getUpstageChatResponse(
            @RequestBody UpstageChatRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        ChatMessageResponse upstageChatResponse = upstageChatService.getUpstageChatResponse(userId, request.content());
        return ResponseEntity.ok().body(upstageChatResponse);
    }
}
