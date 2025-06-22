package com.example.transferservice.listener;

import com.example.common.event.TransferFailedEvent;
import com.example.transferservice.service.TransferStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferFailedListener {

    private final TransferStatusService transferStatusService;

    @RabbitListener(queues = "transfer.failed")
    public void listen(TransferFailedEvent event) {
        transferStatusService.markFailed(event);
    }
}
