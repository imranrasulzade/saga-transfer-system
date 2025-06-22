package com.example.walletservice.service;

import com.example.common.event.*;
import com.example.walletservice.entity.WalletEntity;
import com.example.walletservice.event.EventPublisher;
import com.example.walletservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final EventPublisher eventPublisher;

    public void handleDebit(UUID transactionId, UUID userId, BigDecimal amount) {
        WalletEntity wallet = walletRepository.findByUserId(userId).orElse(null);
        if (wallet == null || wallet.getBalance().compareTo(amount) < 0) {
            eventPublisher.publishWalletDebitFailed(new WalletDebitFailedEvent(transactionId, userId, "Insufficient balance or wallet not found"));
            return;
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
        eventPublisher.publishWalletDebited(new WalletDebitedEvent(transactionId, userId, amount));
    }

    public void handleCredit(UUID transactionId, UUID userId, BigDecimal amount) {
        WalletEntity wallet = walletRepository.findByUserId(userId).orElse(null);
        if (wallet == null) {
            eventPublisher.publishWalletCreditFailed(new WalletCreditFailedEvent(transactionId, userId, "Wallet not found"));
            return;
        }

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
        eventPublisher.publishWalletCredited(new WalletCreditedEvent(transactionId, userId, amount));
    }

    public void handleRefund(UUID transactionId, UUID userId, BigDecimal amount) {
        WalletEntity wallet = walletRepository.findByUserId(userId).orElse(null);
        if (wallet == null) return;

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
        eventPublisher.publishWalletRefunded(new WalletRefundedEvent(transactionId, userId, amount));
    }
}
