package com.example.notificationservice.listener;

import com.example.common.dto.NotifyUserEvent;
import com.example.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotifyUserEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification.queue")
    public void listen(NotifyUserEvent event) {
        notificationService.notify(event);
    }
}
