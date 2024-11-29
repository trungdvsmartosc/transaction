package com.smartosc.transaction.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "transactions")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;

    @Column(nullable = false)
    private double amount;

    @Column(length = 50)
    private String status;

    @Column(length = 50)
    private String type;
    private String remarks;
    private Instant transactionDate;


    public static class STATUS {
        private STATUS() {
        }

        public final static String SUCCESS = "success";
        public final static String FAILED = "failed";
        public final static String PROCESSING = "processing";
    }

    public static class TYPE {
        private TYPE() {
        }

        public final static String DEPOSIT = "deposit";
        public final static String WITHDRAWAL = "withdrawal";
        public final static String TRANSFER = "transfer";
    }
}
