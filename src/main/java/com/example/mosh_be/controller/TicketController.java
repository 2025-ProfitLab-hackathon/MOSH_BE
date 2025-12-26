package com.example.mosh_be.controller;

import com.example.mosh_be.dto.ticket.TicketVerifyRequest;
import com.example.mosh_be.dto.ticket.TicketVerifyResponse;
import com.example.mosh_be.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/verify")
    public ResponseEntity<TicketVerifyResponse> verifyTicket(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody TicketVerifyRequest request
    ) {
        TicketVerifyResponse response = ticketService.verifyTicket(userId, request);

        HttpStatus status = response.getRewardGranted() > 0 ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity.status(status).body(response);
    }
}
