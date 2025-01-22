package com.ayaan.FinanceTracker.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Integer expenseId;

    @ManyToOne
    @JoinColumn(name = "expense_source_id")
    private IncomeExpenseSources incomeExpenseSourceId;

    @Column(name = "name")
	private String name;

    @ManyToOne
    @JoinColumn(name = "bank_acc_id")
	private BankAccount bankAccId; 

    @Column(name = "expense")
    private double expense;

    @Column(name = "expense_date")
    private Date date;

    public Expense(){
        
    }

    public Expense(String name, BankAccount bankAccId, double expense, IncomeExpenseSources incomeExpenseSource, Date date) {
        this.name = name;
        this.bankAccId = bankAccId;
        this.expense = expense;
        this.incomeExpenseSourceId = incomeExpenseSource;
        this.date = date;
    }

    public Expense(Integer expenseId, String name, BankAccount bankAccId, double expense, IncomeExpenseSources incomeExpenseSource, Date date) {
        this(name, bankAccId, expense, incomeExpenseSource, date);
        this.expenseId = expenseId; 
    }

    public Integer getExpenseId() {
        return expenseId;
    }
    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }
    public IncomeExpenseSources getExpenseSourceId() {
        return incomeExpenseSourceId;
    }
    public void setExpenseSourceId(IncomeExpenseSources incomeExpenseSourceId) {
        this.incomeExpenseSourceId = incomeExpenseSourceId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BankAccount getBankAccId() {
        return bankAccId;
    }
    public void setBankAccId(BankAccount bankAccId) {
        this.bankAccId = bankAccId;
    }
    public double getExpense() {
        return expense;
    }
    public void setExpense(double expense) {
        this.expense = expense;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}
