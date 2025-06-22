// WalletCreditFailedListener.java
package com.example.sagacoordinator.listener;

import com.example.common.event.WalletCreditFailedEvent;
import com.example.sagacoordinator.service.SagaCoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletCreditFailedListener {
    private final SagaCoordinatorService sagaService;

    @RabbitListener(queues = "wallet.credit.failed")
    public void listen(WalletCreditFailedEvent event) {
        sagaService.handleCreditFailed(event);
    }
}
