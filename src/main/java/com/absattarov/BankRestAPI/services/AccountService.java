package com.absattarov.BankRestAPI.services;

import com.absattarov.BankRestAPI.models.Account;
import com.absattarov.BankRestAPI.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Сохранение нового счета в базу данных
    @Transactional
    public void save(Account account){
        account.setLimitDatetime(new Date());
        if(account.getBalance()==0){
            account.setBalance(10000);
        }
        if(account.getLimitSum()==0){
            account.setBalance(1000);
            account.setCurrentLimitSum(1000);
        }
        account.setLimitCurrencyShortname("USD");
        accountRepository.save(account);
    }
    // Получение счета по id
    public Account getById(int id){
       return accountRepository.findById(id).orElse(null);
    }
    // Получение списка всех счетов
    public List<Account> getAll(){
        return accountRepository.findAll();
    }
    // Обновление лимита транзакции для конкретного счета
    @Transactional
    public void updateLimit(int id, double limit){
        Account account = getById(id);
        if(account!=null){
            account.setLimitSum(limit);
            account.setCurrentLimitSum(limit);
            accountRepository.save(account);
            account.setLimitDatetime(new Date());
        }
    }
    // Обновление текущего лимита счета
    @Transactional
    public void updateCurrentLimit(int id, double limit){
        Account account = getById(id);
        if(account!=null){
            double balance = account.getCurrentLimitSum();
            account.setCurrentLimitSum(balance-limit);
            accountRepository.save(account);
        }
    }
}


