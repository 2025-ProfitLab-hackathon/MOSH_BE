package com.example.mosh_be.client;

import com.example.mosh_be.client.dto.UpstageChatResponse;
import com.example.mosh_be.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UpstageChatClient {

    RestClient client = RestClient.create();

    @Value("${external.upstage.api-key}")
    private String apiKey;

    private final PromptBuilder promptBuilder;
    private final InMemoryUpstageChatContext chatContext;

    public String getUpstageChatResponse(Long userId, String content) {

        List<Map<String, String>> messages = promptBuilder.upstageChatPromptBuilder(
                chatContext.getMessages(userId),
                content
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", "solar-pro2");
        body.put("messages", messages);
        body.put("stream", false);

        UpstageChatResponse response = client.post()
                .uri("https://api.upstage.ai/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(UpstageChatResponse.class);

        if(response == null) {
            throw new ConflictException("AI 응답이 존재하지 않습니다.");
        }

        String assistantReply = response.choices().get(0).message().content();

        chatContext.addMessage(userId, "user", content);
        chatContext.addMessage(userId, "assistant", assistantReply);

        return assistantReply;
    }
}
