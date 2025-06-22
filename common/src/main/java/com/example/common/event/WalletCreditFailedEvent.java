package com.example.common.event;

import java.util.UUID;

public record WalletCreditFailedEvent(
    UUID transactionId,
    UUID userId,
    String reason
) {}
