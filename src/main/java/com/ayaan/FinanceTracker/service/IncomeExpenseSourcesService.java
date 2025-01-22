package com.ayaan.FinanceTracker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaan.FinanceTracker.daoImpl.IncomeExpenseSourcesDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.models.BudgetTracker;
import com.ayaan.FinanceTracker.models.IncomeExpenseSources;

public class IncomeExpenseSourcesService {
    private static final Logger logger = LoggerFactory.getLogger(IncomeExpenseSourcesService.class);
    
    IncomeExpenseSourcesDAOImpl incomeExpenseSourcesDAO = new IncomeExpenseSourcesDAOImpl();

    public void addIncomeExpenseSource(String incomeExpenseSource, char type, BudgetTracker budgetTracker ) {
        try {
            if (incomeExpenseSource == null || incomeExpenseSource.isEmpty()) {
                throw new IllegalArgumentException("Income source cannot be null or empty");
            }

            IncomeExpenseSources incomeExpenseSources = new IncomeExpenseSources(incomeExpenseSource, type, budgetTracker);
            incomeExpenseSourcesDAO.saveIncomeExpenseSource(incomeExpenseSources);
            logger.info("\nIncome/Expense Source added successfully");

        } catch (Exception e) {
            logger.info("Error: Invalid input");
            e.printStackTrace();
        }
    }

    public void monthlyGoal(String source,  Double goal){   
        try {
            incomeExpenseSourcesDAO.updateMonthlyBudget(source, goal);

        } catch (Exception e) {
            logger.info("Error: Invalid Input ");
            e.printStackTrace();
        }
    }

    public void listSources() {
        try {
            List<IncomeExpenseSources> sources = incomeExpenseSourcesDAO.getAllIncomeExpenseSource();
            if (sources == null) {
                throw new DataAccessException("No Sources found");
            }

            System.out.println("\nSources List: ");
            System.out.printf("%-15s %-15s%n",
                    "Sources" , "Type");
            System.out.println("---------------------");
            sources.forEach(incomeExpenseSources -> System.out.printf("%-15s %-15s%n",
                    incomeExpenseSources.getIncomeExpenseSource(), incomeExpenseSources.getType()));

        } catch(DataAccessException e){  
            logger.error("Database error while fetching income sources: {}", e.getMessage());
        } catch (Exception e) {
            logger.info("An unexpected error occurred.");
            e.printStackTrace();
        }
    }
}
