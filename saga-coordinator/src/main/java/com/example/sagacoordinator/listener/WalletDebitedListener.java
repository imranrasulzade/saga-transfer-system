// WalletDebitedListener.java
package com.example.sagacoordinator.listener;

import com.example.common.event.WalletDebitedEvent;
import com.example.sagacoordinator.service.SagaCoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletDebitedListener {
    private final SagaCoordinatorService sagaService;

    @RabbitListener(queues = "wallet.debited")
    public void listen(WalletDebitedEvent event) {
        sagaService.handleDebitSuccess(event);
    }
}
