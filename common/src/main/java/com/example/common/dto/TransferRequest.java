package com.example.common.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
    UUID fromUserId,
    UUID toUserId,
    BigDecimal amount
) {}
