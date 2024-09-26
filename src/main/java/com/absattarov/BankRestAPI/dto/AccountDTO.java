package com.absattarov.BankRestAPI.dto;

public class AccountDTO {
    private int id;
    private double limit_sum;

    public AccountDTO() {
    }

    public AccountDTO(int id, double limit_sum) {
        this.id = id;
        this.limit_sum = limit_sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLimit_sum() {
        return limit_sum;
    }

    public void setLimit_sum(double limit_sum) {
        this.limit_sum = limit_sum;
    }
}
