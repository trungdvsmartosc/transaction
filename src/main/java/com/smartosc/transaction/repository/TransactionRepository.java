package com.smartosc.transaction.repository;

import com.smartosc.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.senderAccount.id = :id or t.receiverAccount.id = :id")
    List<Transaction> findTransactionsBySenderAccountOrReceiverAccountId(@Param("id") Long id);
}
