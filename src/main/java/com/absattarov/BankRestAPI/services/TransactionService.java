package com.absattarov.BankRestAPI.services;

import com.absattarov.BankRestAPI.models.Account;
import com.absattarov.BankRestAPI.models.Transaction;
import com.absattarov.BankRestAPI.repositories.AccountRepository;
import com.absattarov.BankRestAPI.repositories.ExchangeRateRepository;
import com.absattarov.BankRestAPI.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransactionService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, ExchangeRateRepository exchangeRateRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    // Получение отправленных транзакции одного конкретного счета
    public List<Transaction> getRelatedTransaction(int id){
        return transactionRepository.findAllByAccountFrom_IdOrAccountTo_IdOrderByTransactionDateDesc(id,id);
    }
    // Получение отправленных транзакции с превышением лимита одного конкретного счета
    public List<Transaction> getLimitExceeded(int id){
        Account account = accountRepository.getReferenceById(id);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);

        LocalDate now = LocalDate.now();

        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());

        return transactionRepository.findAllByAccountFromIsAndTransactionDateBetweenAndLimitExceededIsTrue(account,
                c.getTime(),Date.from(lastDay.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
    // Сохранение в базу данных новой транзакции
    @Transactional
    public void save(Transaction transaction){
        if(transaction.getAccountTo()!=null && transaction.getAccountFrom()!=null && transaction.getCurrencyShortname()!=null){
            Account accountFrom = transaction.getAccountFrom();
            double currentExhangeRate, kzt_rubToUSD=0,accountBalancekzt_rubToUSD=0;
            if(transaction.getCurrencyShortname().equals("KZT")){
                currentExhangeRate = exchangeRateRepository.findTopByOrderByCloseDateDesc().getTng_usd();
                kzt_rubToUSD = transaction.getSum()/currentExhangeRate;
            } else if (transaction.getCurrencyShortname().equals("RUB")) {
                currentExhangeRate = exchangeRateRepository.findTopByOrderByCloseDateDesc().getRub_usd();
                kzt_rubToUSD = transaction.getSum()/currentExhangeRate;
            }

            if(accountFrom.getCurrencyShortname().equals("KZT")){
                currentExhangeRate = exchangeRateRepository.findTopByOrderByCloseDateDesc().getTng_usd();
                accountBalancekzt_rubToUSD = accountFrom.getBalance()/currentExhangeRate;
                accountFrom.setBalance(Math.round((accountBalancekzt_rubToUSD-kzt_rubToUSD)*currentExhangeRate*100.0)/100.0);
            } else if (accountFrom.getCurrencyShortname().equals("RUB")) {
                currentExhangeRate = exchangeRateRepository.findTopByOrderByCloseDateDesc().getRub_usd();
                accountBalancekzt_rubToUSD = accountFrom.getBalance()/currentExhangeRate;
                accountFrom.setBalance(Math.round((accountBalancekzt_rubToUSD-kzt_rubToUSD)*currentExhangeRate*100.0)/100.0);
            }

            double currentLimitSum = accountFrom.getCurrentLimitSum();
            if(currentLimitSum-kzt_rubToUSD<0){
                transaction.setLimitExceeded(true);
            }
            else {
                transaction.setLimitExceeded(false);
            }

                double balance = accountFrom.getCurrentLimitSum();
                accountFrom.setCurrentLimitSum((Math.round(balance-kzt_rubToUSD)*100.0)/100.0);

                accountRepository.save(accountFrom);
            Account accountTo = transaction.getAccountTo();
            if(accountTo.getCurrencyShortname().equals("KZT")){
                accountTo.setBalance(accountTo.getBalance()+kzt_rubToUSD*exchangeRateRepository.findTopByOrderByCloseDateDesc().getTng_usd());
            } else if (accountTo.getCurrencyShortname().equals("RUB")) {
                accountTo.setBalance(accountTo.getBalance()+kzt_rubToUSD*exchangeRateRepository.findTopByOrderByCloseDateDesc().getRub_usd());
            }
            accountRepository.save(accountTo);
            transaction.setExpenseCategory("Товары/Услуги");
            transaction.setTransactionDate(new Date());

            transactionRepository.save(transaction);
        }
    }
}
