package com.absattarov.BankRestAPI.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "account_from", referencedColumnName = "id")
    private Account accountFrom;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "account_to", referencedColumnName = "id")
    private Account accountTo;
    @Column(name = "currency_shortname")
    private String currencyShortname;
    @Column(name = "sum")
    private double sum;
    @Column(name = "expense_category")
    private String expenseCategory;
    @Column(name = "transaction_date")
    private Date transactionDate;
    @Column(name = "limit_exceeded")
    private boolean limitExceeded;

    public Transaction() {
    }

    public Transaction(int id, Account accountFrom, Account accountTo, String currencyShortname, double sum, String expenseCategory, Date transactionDate, boolean limitExceeded) {
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

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
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
