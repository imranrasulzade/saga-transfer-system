package com.example.common.event;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletCreditedEvent(
    UUID transactionId,
    UUID userId,
    BigDecimal amount
) {}
