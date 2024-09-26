package com.absattarov.BankRestAPI.repositories;

import com.absattarov.BankRestAPI.models.Account;
import com.absattarov.BankRestAPI.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    List<Transaction> findAllByAccountFrom_IdOrAccountTo_IdOrderByTransactionDateDesc(int id,int id2);
    List<Transaction> findAllByAccountFromIsAndTransactionDateBetweenAndLimitExceededIsTrue(Account account, Date start, Date end);
}
