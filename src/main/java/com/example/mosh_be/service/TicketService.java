package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.RewardTransaction;
import com.example.mosh_be.domain.entity.Ticket;
import com.example.mosh_be.domain.entity.User;
import com.example.mosh_be.domain.enums.RewardTransactionType;
import com.example.mosh_be.dto.ticket.TicketVerifyRequest;
import com.example.mosh_be.dto.ticket.TicketVerifyResponse;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.RewardTransactionRepository;
import com.example.mosh_be.repository.TicketRepository;
import com.example.mosh_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private static final int TICKET_REWARD = 3000;

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final RewardTransactionRepository rewardTransactionRepository;

    @Transactional
    public TicketVerifyResponse verifyTicket(Long userId, TicketVerifyRequest request) {
        Ticket ticket = ticketRepository.findByTicketNumber(request.getTicketNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        int rewardGranted = 0;

        if (!ticket.getIsUsed()) {
            // Grant reward
            ticket.verify();
            user.addReward(TICKET_REWARD);
            rewardGranted = TICKET_REWARD;

            // Create reward transaction
            RewardTransaction transaction = RewardTransaction.builder()
                    .userId(userId)
                    .type(RewardTransactionType.TICKET)
                    .amount(TICKET_REWARD)
                    .build();
            rewardTransactionRepository.save(transaction);
        }

        return TicketVerifyResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .isUsed(ticket.getIsUsed())
                .verifiedAt(ticket.getVerifiedAt())
                .rewardGranted(rewardGranted)
                .rewardBalance(user.getReward())
                .build();
    }
}
