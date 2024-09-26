package com.absattarov.BankRestAPI.controllers;

import com.absattarov.BankRestAPI.dto.AccountDTO;
import com.absattarov.BankRestAPI.dto.TransactionDTO;
import com.absattarov.BankRestAPI.models.Account;
import com.absattarov.BankRestAPI.models.ExchangeRate;
import com.absattarov.BankRestAPI.models.Transaction;
import com.absattarov.BankRestAPI.services.AccountService;
import com.absattarov.BankRestAPI.services.ExchangeService;
import com.absattarov.BankRestAPI.services.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {
    private final ExchangeService exchangeService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public BankController(ExchangeService exchangeService, AccountService accountService, TransactionService transactionService) {
        this.exchangeService = exchangeService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/update_exchange") // Получение нового курса валют и сохранение его в базу данных
    public String updateExchange() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String accessKey = "befaa3a5dff8e6013fe2f9d09ee1e88c";
        String url = "https://api.currencylayer.com/live?access_key="+accessKey+"&source = EUR&currencies = KZT,RUB&format = 1";
        String response = restTemplate.getForObject(url,String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode obj = objectMapper.readTree(response);
        String kzt = String.valueOf(obj.get("quotes").get("USDKZT"));
        String rub = String.valueOf(obj.get("quotes").get("USDRUB"));

        ExchangeRate exchangeRate = new ExchangeRate();
        double kztDouble = Math.round(Double.parseDouble(kzt)*100.0)/100.0;
        double rubDouble = Math.round(Double.parseDouble(rub)*100.0)/100.0;
        exchangeRate.setTng_usd(kztDouble);
        exchangeRate.setRub_usd(rubDouble);

        exchangeService.save(exchangeRate);

        return kzt+" \n"+rub;
    }
    @GetMapping("/accounts") // Получение всех счетов
    public List<Account> getAccounts(){
        return accountService.getAll();
    }
    @GetMapping("/transactions/{id}")  // Получение всех исходящих транзакции конкретного счета
    public List<Transaction> getTransactions(@PathVariable int id){
        return transactionService.getRelatedTransaction(id);
    }
    @GetMapping("/exceed-transactions/{id}") // Получение всех исходящих транзакции с превышением лимата конкретного счета
    public List<Transaction> getExceededTransactions(@PathVariable int id){
        return transactionService.getLimitExceeded(id);
    }
    @PostMapping("/create")  // Создание нового счета
    public ResponseEntity<HttpStatus> createAccount(@RequestBody @Valid Account account){
        accountService.save(account);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/create_transaction")  // Создание новой транзакции
    public ResponseEntity<HttpStatus> createTransaction(@RequestBody TransactionDTO transactionDTO){
        Transaction transaction = new Transaction();
        transaction.setSum(transactionDTO.getSum());
        transaction.setAccountFrom(accountService.getById(transactionDTO.getAccountFrom()));
        transaction.setAccountTo(accountService.getById(transactionDTO.getAccountTo()));
        transaction.setCurrencyShortname(transactionDTO.getCurrencyShortname());
        transactionService.save(transaction);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/update_limit")  // Обновление лимита транзакции для конкретного счета
    public ResponseEntity<HttpStatus> updateLimit(@RequestBody AccountDTO accountDTO){
        accountService.updateLimit(accountDTO.getId(),accountDTO.getLimit_sum());
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
