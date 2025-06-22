//package com.example.sagacoordinator.entity;
//
//import com.example.common.enums.TransactionStatus;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//@Entity
//@Table(name = "transactions")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class TransactionEntity {
//
//    @Id
//    private UUID id;
//
//    private UUID fromUserId;
//    private UUID toUserId;
//    private BigDecimal amount;
//
//    @Enumerated(EnumType.STRING)
//    private TransactionStatus status;
//}
