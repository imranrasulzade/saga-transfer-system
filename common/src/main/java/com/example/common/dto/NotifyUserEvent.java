package com.example.common.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record NotifyUserEvent(
    UUID fromUserId,
    UUID toUserId,
    BigDecimal amount,
    boolean success,
    String message
) {}
