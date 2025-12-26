package com.example.mosh_be.dto.chatbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ChatMessageResponse {
    private String answer;
    private List<String> citations;
    private String traceId;
}
