package com.ayaan.FinanceTracker.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "income_expense_sources")
public class IncomeExpenseSources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_exp_src_id")
    private Integer incomeExpenseSourceId;

    @Column(name = "income_expense_src")
    private String incomeExpenseSource;
    
    @Column(name = "type")
    private char type;

    @Column(name = "monthly_budget")
    private Double monthlyBudget;
    
    @ManyToOne
    @JoinColumn(name = "budget_tracker_id")
    private BudgetTracker budgetTracker;
    

    public IncomeExpenseSources(){
        
    }
    
    public IncomeExpenseSources(String incomeExpenseSource, char type, BudgetTracker budgetTracker) {
        this.incomeExpenseSource = incomeExpenseSource;
        this.type = type;
        this.budgetTracker = budgetTracker;
    }

    public Integer getIncomeExpenseSourceId() {
        return incomeExpenseSourceId;
    }
    public void setIncomeExpenseSourceId(Integer incomeExpenseSourceId) {
        this.incomeExpenseSourceId = incomeExpenseSourceId;
    }
    
    public String getIncomeExpenseSource() {
        return incomeExpenseSource;
    }
    public void setIncomeExpenseSource(String incomeExpenseSource) {
        this.incomeExpenseSource = incomeExpenseSource;
    }
    public char getType() {
        return type;
    }
    public void setType(char type) {
        this.type = type;
    }

    public Double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(Double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }
    
    public BudgetTracker getBudgetTracker() {
        return budgetTracker;
    }

    public void setBudgetTracker(BudgetTracker budgetTracker) {
        this.budgetTracker = budgetTracker;
    }
}
