package com.absattarov.BankRestAPI.services;

import com.absattarov.BankRestAPI.models.ExchangeRate;
import com.absattarov.BankRestAPI.repositories.ExchangeRateRepository;
import com.absattarov.BankRestAPI.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ExchangeService {
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    public ExchangeService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }
    // Получение последнего созраненного курса валют
    public ExchangeRate getLastRecord(){
        return exchangeRateRepository.findTopByOrderByCloseDateDesc();
    }
    // Сохранение нового курса валют в базу данных
    @Transactional
    public void save(ExchangeRate exchangeRate){
        exchangeRate.setCloseDate(new Date());
        exchangeRateRepository.save(exchangeRate);
    }
}
