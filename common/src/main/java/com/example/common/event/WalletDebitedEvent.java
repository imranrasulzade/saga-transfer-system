package com.example.common.event;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletDebitedEvent(
    UUID transactionId,
    UUID userId,
    BigDecimal amount
) {}
