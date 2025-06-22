package com.example.common.event;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferInitiatedEvent(
    UUID transactionId,
    UUID fromUserId,
    UUID toUserId,
    BigDecimal amount
) {}
