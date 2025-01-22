package com.ayaan.FinanceTracker.dao;

import java.util.List;

import com.ayaan.FinanceTracker.models.IncomeExpenseSources;

public interface IncomeExpenseSourcesDAO {

    void saveIncomeExpenseSource(IncomeExpenseSources incomeExpenseSources);

    void updateIncomeExpenseSources(IncomeExpenseSources incomeExpenseSources);

    void updateMonthlyBudget(String sourceName, Double newBudget);

    void deleteIncome(Integer id);

    IncomeExpenseSources getIncomeExpenseSourceByCondition(String source);

    IncomeExpenseSources getIncomeExpenseSourcesbyId(Integer id);

    List<IncomeExpenseSources> getAllIncomeExpenseSources(char type);

    List<IncomeExpenseSources> getAllIncomeExpenseSource();
}
