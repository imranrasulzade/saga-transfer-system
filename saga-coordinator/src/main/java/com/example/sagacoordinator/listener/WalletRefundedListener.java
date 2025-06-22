// WalletRefundedListener.java
package com.example.sagacoordinator.listener;

import com.example.common.event.WalletRefundedEvent;
import com.example.sagacoordinator.service.SagaCoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletRefundedListener {
    private final SagaCoordinatorService sagaService;

    @RabbitListener(queues = "wallet.refunded")
    public void listen(WalletRefundedEvent event) {
        sagaService.handleRefunded(event);
    }
}
