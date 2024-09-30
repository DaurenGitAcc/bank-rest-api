package com.absattarov.BankRestAPI.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "balance")
    private double balance;
    @Column(name = "currency_shortname")
    @Size(max = 3)
    @NotEmpty
    private String currencyShortname;
    @Column(name = "limit_sum")
    private double limitSum;
    @Column(name = "limit_datetime")
    private Date limitDatetime;
    @Column(name = "limit_currency_shortname")
    private String limitCurrencyShortname;
    @Column(name = "current_limit_sum")
    private double currentLimitSum;

    @JsonManagedReference
    @OneToMany(mappedBy = "accountFrom")
    private List<Transaction> transactionsFrom;
    @JsonManagedReference
    @OneToMany(mappedBy = "accountTo")
    private List<Transaction> transactionsTo;

    public Account() {
    }

    public Account(int id, double balance, String currencyShortname, double limitSum, Date limitDatetime, String limitCurrencyShortname, double currentLimitSum) {
        this.id = id;
        this.balance = balance;
        this.currencyShortname = currencyShortname;
        this.limitSum = limitSum;
        this.limitDatetime = limitDatetime;
        this.limitCurrencyShortname = limitCurrencyShortname;
        this.currentLimitSum = currentLimitSum;
    }

    public Account(int id, double balance, String currencyShortname, double limitSum, Date limitDatetime, String limitCurrencyShortname, double currentLimitSum, List<Transaction> transactionsFrom, List<Transaction> transactionsTo) {
        this.id = id;
        this.balance = balance;
        this.currencyShortname = currencyShortname;
        this.limitSum = limitSum;
        this.limitDatetime = limitDatetime;
        this.limitCurrencyShortname = limitCurrencyShortname;
        this.currentLimitSum = currentLimitSum;
        this.transactionsFrom = transactionsFrom;
        this.transactionsTo = transactionsTo;
    }

    public Account(int id, double balance, String currencyShortname, double limitSum, Date limitDatetime, String limitCurrencyShortname, List<Transaction> transactionsFrom, List<Transaction> transactionsTo) {
        this.id = id;
        this.balance = balance;
        this.currencyShortname = currencyShortname;
        this.limitSum = limitSum;
        this.limitDatetime = limitDatetime;
        this.limitCurrencyShortname = limitCurrencyShortname;
        this.transactionsFrom = transactionsFrom;
        this.transactionsTo = transactionsTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }



    public List<Transaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    public void setTransactionsFrom(List<Transaction> transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

    public List<Transaction> getTransactionsTo() {
        return transactionsTo;
    }

    public void setTransactionsTo(List<Transaction> transactionsTo) {
        this.transactionsTo = transactionsTo;
    }

    public double getCurrentLimitSum() {
        return currentLimitSum;
    }

    public void setCurrentLimitSum(double currentLimitSum) {
        this.currentLimitSum = currentLimitSum;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrencyShortname() {
        return currencyShortname;
    }

    public void setCurrencyShortname(String currencyShortname) {
        this.currencyShortname = currencyShortname;
    }

    public double getLimitSum() {
        return limitSum;
    }

    public void setLimitSum(double limitSum) {
        this.limitSum = limitSum;
    }

    public Date getLimitDatetime() {
        return limitDatetime;
    }

    public void setLimitDatetime(Date limitDatetime) {
        this.limitDatetime = limitDatetime;
    }

    public String getLimitCurrencyShortname() {
        return limitCurrencyShortname;
    }

    public void setLimitCurrencyShortname(String limitCurrencyShortname) {
        this.limitCurrencyShortname = limitCurrencyShortname;
    }
}
