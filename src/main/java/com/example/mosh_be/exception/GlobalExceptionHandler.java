package com.example.mosh_be.exception;

import com.example.mosh_be.dto.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String traceId = generateTraceId();
        ErrorResponse error = ErrorResponse.builder()
                .code("BAD_REQUEST")
                .message("요청 파라미터가 올바르지 않습니다.")
                .traceId(traceId)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String traceId = generateTraceId();
        ErrorResponse error = ErrorResponse.builder()
                .code("NOT_FOUND")
                .message(ex.getMessage())
                .traceId(traceId)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        String traceId = generateTraceId();
        ErrorResponse error = ErrorResponse.builder()
                .code("UNAUTHORIZED")
                .message(ex.getMessage())
                .traceId(traceId)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        String traceId = generateTraceId();
        ErrorResponse error = ErrorResponse.builder()
                .code("CONFLICT")
                .message(ex.getMessage())
                .traceId(traceId)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        String traceId = generateTraceId();
        ErrorResponse error = ErrorResponse.builder()
                .code("BAD_REQUEST")
                .message(ex.getMessage())
                .traceId(traceId)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        String traceId = generateTraceId();
        ErrorResponse error = ErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("서버 오류가 발생했습니다.")
                .traceId(traceId)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private String generateTraceId() {
        return "t_" + UUID.randomUUID().toString().substring(0, 12);
    }
}
