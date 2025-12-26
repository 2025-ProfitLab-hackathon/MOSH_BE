package com.example.mosh_be.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String code;
    private String message;
    private String traceId;
    private List<Object> details;
}
