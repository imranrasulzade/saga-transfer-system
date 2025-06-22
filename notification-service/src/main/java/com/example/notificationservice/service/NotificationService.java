package com.example.notificationservice.service;

import com.example.common.dto.NotifyUserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void notify(NotifyUserEvent event) {
        if (event.success()) {
            log.info("✅ Notification: Transfer from {} to {} of amount {} was successful. Message: {}",
                    event.fromUserId(), event.toUserId(), event.amount(), event.message());
        } else {
            log.warn("❌ Notification: Transfer from {} to {} of amount {} failed. Message: {}",
                    event.fromUserId(), event.toUserId(), event.amount(), event.message());
        }
    }
}
