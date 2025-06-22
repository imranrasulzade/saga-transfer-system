// WalletCreditedListener.java
package com.example.sagacoordinator.listener;

import com.example.common.event.WalletCreditedEvent;
import com.example.sagacoordinator.service.SagaCoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletCreditedListener {
    private final SagaCoordinatorService sagaService;

    @RabbitListener(queues = "wallet.credited")
    public void listen(WalletCreditedEvent event) {
        sagaService.handleCreditSuccess(event);
    }
}
