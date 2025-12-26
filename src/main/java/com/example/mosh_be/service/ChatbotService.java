package com.example.mosh_be.service;

import com.example.mosh_be.dto.chatbot.ChatMessageRequest;
import com.example.mosh_be.dto.chatbot.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final WebClient.Builder webClientBuilder;

    @Value("${solar.api.url}")
    private String solarApiUrl;

    @Value("${solar.api.key}")
    private String solarApiKey;

    public ChatMessageResponse sendMessage(Long userId, ChatMessageRequest request) {
        // Mock implementation for MVP
        String answer = "페스티벌에서 즐거운 시간 보내세요! 오후 6시 이후에는 메인 스테이지의 헤드라이너 공연을 추천합니다.";

        return ChatMessageResponse.builder()
                .answer(answer)
                .citations(new ArrayList<>())
                .traceId("t_" + System.currentTimeMillis())
                .build();

        /* Real implementation would be:
        try {
            WebClient webClient = webClientBuilder.baseUrl(solarApiUrl).build();

            Map<String, Object> requestBody = Map.of(
                "messages", List.of(Map.of("role", "user", "content", request.getMessage())),
                "model", "solar-1-mini-chat"
            );

            Map<String, Object> response = webClient.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + solarApiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            String answer = extractAnswer(response);

            return ChatMessageResponse.builder()
                    .answer(answer)
                    .citations(new ArrayList<>())
                    .traceId("t_" + System.currentTimeMillis())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get chatbot response", e);
        }
        */
    }
}
