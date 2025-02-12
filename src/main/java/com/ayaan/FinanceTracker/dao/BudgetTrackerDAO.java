package com.ayaan.FinanceTracker.dao;

import java.util.List;

import com.ayaan.FinanceTracker.models.BudgetTracker;

public interface BudgetTrackerDAO {

    boolean saveBudget(BudgetTracker budgetTracker);
    
    void updateBudget(BudgetTracker budgetTracker);
    
    void deleteBudget(BudgetTracker budgetTracker);
    
    BudgetTracker getBudgetById(Integer id);
    
    List<Object[]> displayIncome();
    
    List<Object[]> displayExpense();
    
    BudgetTracker getBudgetByCondition(String source);
    
    List<BudgetTracker> getAllBudgets();
}
