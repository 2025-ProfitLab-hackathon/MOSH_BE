package com.example.mosh_be.service;

import com.example.mosh_be.client.UpstageChatClient;
import com.example.mosh_be.dto.chatbot.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpstageChatService {

    private final UpstageChatClient client;

    public ChatMessageResponse getUpstageChatResponse(Long userId, String content) {
        String answer = client.getUpstageChatResponse(userId, content);
        return new ChatMessageResponse(
                answer
        );
    }
}
