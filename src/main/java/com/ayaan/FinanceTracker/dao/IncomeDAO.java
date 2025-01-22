package com.ayaan.FinanceTracker.dao;

import java.util.List;

import com.ayaan.FinanceTracker.models.Income;

public interface IncomeDAO {

    void updateIncome(Income income);

    void deleteIncome(Income income);

    Income getIncomebyId(Integer id);

    List<Income> getAllIncome();

    Double getIncomes();

    Double getIncomeBySourceFromIncomes(String source);

    void saveIncome(Income income);
}
