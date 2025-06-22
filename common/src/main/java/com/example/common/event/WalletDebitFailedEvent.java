package com.example.common.event;

import java.util.UUID;

public record WalletDebitFailedEvent(
    UUID transactionId,
    UUID userId,
    String reason
) {}
