package com.ayaan.FinanceTracker.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_acc_id")
    private Integer bankAccId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "account_date")
    private Date accountDate;

    public BankAccount() {
        
    }

    public BankAccount(String name, Date accountDate) {
        this.name = name;
        this.accountDate = accountDate;
    }

    public BankAccount(Integer bankAccId, String name, Date accountDate) {
        this(name, accountDate);
        this.bankAccId = bankAccId;
    }
    
    public Integer getBankAccId() {
        return bankAccId;
    }
    public void setBankAccId(Integer bankAccId) {
        this.bankAccId = bankAccId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getAccountDate() {
        return accountDate;
    }
    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }
}
