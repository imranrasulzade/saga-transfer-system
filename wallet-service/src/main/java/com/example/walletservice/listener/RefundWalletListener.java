package com.example.walletservice.listener;

import com.example.common.event.WalletRefundedEvent;
import com.example.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefundWalletListener {

    private final WalletService walletService;

    @RabbitListener(queues = "wallet.refund.request")
    public void listen(WalletRefundedEvent event) {
        walletService.handleRefund(event.transactionId(), event.userId(), event.amount());
    }
}
