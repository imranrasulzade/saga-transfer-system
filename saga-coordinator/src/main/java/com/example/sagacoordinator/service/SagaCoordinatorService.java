package com.example.sagacoordinator.service;

import com.example.common.dto.TransferRequest;
import com.example.common.enums.TransactionStatus;
import com.example.common.event.*;
import com.example.common.entity.TransactionEntity;
import com.example.sagacoordinator.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SagaCoordinatorService {

    private final TransactionRepository transactionRepository;
    private final RabbitTemplate rabbitTemplate;

    public void initiateTransfer(TransferRequest request) {
        UUID transactionId = UUID.randomUUID();
        transactionRepository.save(TransactionEntity.builder()
                .id(transactionId)
                .fromUserId(request.fromUserId())
                .toUserId(request.toUserId())
                .amount(request.amount())
                .status(TransactionStatus.PENDING)
                .build());

        rabbitTemplate.convertAndSend("wallet.debit", "", new TransferInitiatedEvent(
                transactionId, request.fromUserId(), request.toUserId(), request.amount()));
    }

    public void handleDebitSuccess(WalletDebitedEvent event) {
        rabbitTemplate.convertAndSend("wallet.credit", "", new TransferInitiatedEvent(
                event.transactionId(), null, event.userId(), event.amount()));
    }

    public void handleCreditSuccess(WalletCreditedEvent event) {
        rabbitTemplate.convertAndSend("transfer.status", "", new TransferSuccessEvent(
                event.transactionId(), true, null));
    }

    public void handleDebitFailed(WalletDebitFailedEvent event) {
        rabbitTemplate.convertAndSend("transfer.status", "", new TransferFailedEvent(
                event.transactionId(), false, event.reason()));
    }

    public void handleCreditFailed(WalletCreditFailedEvent event) {
        rabbitTemplate.convertAndSend("wallet.refund", "", new WalletRefundedEvent(
                event.transactionId(), null, null)); // optional: fill values
    }

    public void handleRefunded(WalletRefundedEvent event) {
        rabbitTemplate.convertAndSend("transfer.status", "", new TransferFailedEvent(
                event.transactionId(), false, "Credit failed. Refunded."));
    }
}
