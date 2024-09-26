package com.absattarov.BankRestAPI.dto;

import com.absattarov.BankRestAPI.models.Account;
import jakarta.persistence.*;

import java.util.Date;

public class TransactionDTO {

    private int id;

    private int accountFrom;

    private int accountTo;

    private String currencyShortname;
    private double sum;

    private String expenseCategory;

    private Date transactionDate;

    private boolean limitExceeded;

    public TransactionDTO() {
    }

    public TransactionDTO(int id, int accountFrom, int accountTo, String currencyShortname, double sum, String expenseCategory, Date transactionDate, boolean limitExceeded) {
        this.id = id;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currencyShortname = currencyShortname;
        this.sum = sum;
        this.expenseCategory = expenseCategory;
        this.transactionDate = transactionDate;
        this.limitExceeded = limitExceeded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public String getCurrencyShortname() {
        return currencyShortname;
    }

    public void setCurrencyShortname(String currencyShortname) {
        this.currencyShortname = currencyShortname;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public boolean isLimitExceeded() {
        return limitExceeded;
    }

    public void setLimitExceeded(boolean limitExceeded) {
        this.limitExceeded = limitExceeded;
    }
}
