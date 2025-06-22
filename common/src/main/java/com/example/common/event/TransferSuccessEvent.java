package com.example.common.event;

import java.util.UUID;

public record TransferSuccessEvent(
    UUID transactionId,
    boolean success,
    String failureReason // should be null if success is true
) {}
