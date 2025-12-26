package com.example.mosh_be.client;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUpstageChatContext {

    private static final int MAX_SIZE = 10;

    private final Map<Long, Deque<Map<String, String>>> userMemory
            = new ConcurrentHashMap<>();

    public void addMessage(Long userId, String role, String content) {
        Deque<Map<String, String>> memory =
                userMemory.computeIfAbsent(userId, id -> new ArrayDeque<>());

        if (memory.size() >= MAX_SIZE) {
            memory.pollFirst();
        }

        memory.addLast(Map.of(
                "role", role,
                "content", content
        ));
    }

    public List<Map<String, String>> getMessages(Long userId) {
        return List.copyOf(
                userMemory.getOrDefault(userId, new ArrayDeque<>())
        );
    }
}
