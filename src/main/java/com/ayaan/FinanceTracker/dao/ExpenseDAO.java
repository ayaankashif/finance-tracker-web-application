package com.ayaan.FinanceTracker.dao;

import java.util.List;

import com.ayaan.FinanceTracker.models.Expense;

public interface ExpenseDAO {

    void saveExpense(Expense expense);
    
    void updateExpense(Expense expense);
    
    void deleteExpense(Expense expense);
    
    Expense getExpensebyId(Integer id);
    
    List<Expense> getAllExpense();
    
    Double getExpenses();
    
    Double getExpenseBySourceFromExpense(String source);
    
    Double getExpensesBySourceId(Integer id);
}
