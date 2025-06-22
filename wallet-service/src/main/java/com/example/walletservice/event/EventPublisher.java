package com.example.walletservice.event;

import com.example.common.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishWalletDebited(WalletDebitedEvent event) {
        rabbitTemplate.convertAndSend("wallet.debit", "", event);
    }

    public void publishWalletDebitFailed(WalletDebitFailedEvent event) {
        rabbitTemplate.convertAndSend("wallet.debit", "", event);
    }

    public void publishWalletCredited(WalletCreditedEvent event) {
        rabbitTemplate.convertAndSend("wallet.credit", "", event);
    }

    public void publishWalletCreditFailed(WalletCreditFailedEvent event) {
        rabbitTemplate.convertAndSend("wallet.credit", "", event);
    }

    public void publishWalletRefunded(WalletRefundedEvent event) {
        rabbitTemplate.convertAndSend("wallet.refund", "", event);
    }
}
