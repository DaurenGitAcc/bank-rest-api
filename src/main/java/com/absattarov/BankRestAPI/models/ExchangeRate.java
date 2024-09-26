package com.absattarov.BankRestAPI.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "tng_usd")
    private double tng_usd;
    @Column(name = "rub_usd")
    private double rub_usd;
    @Column(name = "closedate")
    private Date closeDate;

    public ExchangeRate() {
    }

    public ExchangeRate(int id, double tng_usd, double rub_usd, Date closeDate) {
        this.id = id;
        this.tng_usd = tng_usd;
        this.rub_usd = rub_usd;
        this.closeDate = closeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTng_usd() {
        return tng_usd;
    }

    public void setTng_usd(double tng_usd) {
        this.tng_usd = tng_usd;
    }

    public double getRub_usd() {
        return rub_usd;
    }

    public void setRub_usd(double rub_usd) {
        this.rub_usd = rub_usd;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
}
