package com.example.common.event;

import java.util.UUID;

public record TransferFailedEvent(
    UUID transactionId,
    boolean success,
    String failureReason
) {}
