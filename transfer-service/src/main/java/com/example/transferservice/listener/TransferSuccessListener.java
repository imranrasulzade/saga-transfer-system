package com.example.transferservice.listener;

import com.example.common.event.TransferSuccessEvent;
import com.example.transferservice.service.TransferStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferSuccessListener {

    private final TransferStatusService transferStatusService;

    @RabbitListener(queues = "transfer.success")
    public void listen(TransferSuccessEvent event) {
        transferStatusService.markSuccess(event);
    }
}
