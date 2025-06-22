package com.example.walletservice.listener;

import com.example.common.event.TransferInitiatedEvent;
import com.example.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebitWalletListener {

    private final WalletService walletService;

    @RabbitListener(queues = "wallet.debit.request")
    public void listen(TransferInitiatedEvent event) {
        walletService.handleDebit(event.transactionId(), event.fromUserId(), event.amount());
    }
}
