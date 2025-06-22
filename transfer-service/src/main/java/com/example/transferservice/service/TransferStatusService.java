package com.example.transferservice.service;

import com.example.common.dto.NotifyUserEvent;
import com.example.common.enums.TransactionStatus;
import com.example.common.event.TransferFailedEvent;
import com.example.common.event.TransferSuccessEvent;
import com.example.common.entity.TransactionEntity;
import com.example.sagacoordinator.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferStatusService {

    private final TransactionRepository transactionRepository;
    private final RabbitTemplate rabbitTemplate;

    public void markSuccess(TransferSuccessEvent event) {
        TransactionEntity transaction = transactionRepository.findById(event.transactionId()).orElseThrow();
        transaction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);

        rabbitTemplate.convertAndSend("notification", "", new NotifyUserEvent(
                transaction.getFromUserId(),
                transaction.getToUserId(),
                transaction.getAmount(),
                true,
                "Transfer completed successfully"
        ));
    }

    public void markFailed(TransferFailedEvent event) {
        TransactionEntity transaction = transactionRepository.findById(event.transactionId()).orElseThrow();
        transaction.setStatus(TransactionStatus.FAILED);
        transactionRepository.save(transaction);

        rabbitTemplate.convertAndSend("notification", "", new NotifyUserEvent(
                transaction.getFromUserId(),
                transaction.getToUserId(),
                transaction.getAmount(),
                false,
                "Transfer failed: " + event.failureReason()
        ));
    }
}
