package com.example.securityl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "transaction_date")
    private Date transactionDate;
}
