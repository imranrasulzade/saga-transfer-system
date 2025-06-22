// WalletDebitFailedListener.java
package com.example.sagacoordinator.listener;

import com.example.common.event.WalletDebitFailedEvent;
import com.example.sagacoordinator.service.SagaCoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletDebitFailedListener {
    private final SagaCoordinatorService sagaService;

    @RabbitListener(queues = "wallet.debit.failed")
    public void listen(WalletDebitFailedEvent event) {
        sagaService.handleDebitFailed(event);
    }
}
