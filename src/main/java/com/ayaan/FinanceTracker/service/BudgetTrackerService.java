package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaan.FinanceTracker.models.Expense;
import com.ayaan.FinanceTracker.daoImpl.BudgetTrackerDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.ExpenseDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.IncomeDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.IncomeExpenseSourcesDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.exceptionHandling.ExceedsPercentageException;
import com.ayaan.FinanceTracker.models.AccountTransaction;
import com.ayaan.FinanceTracker.models.BudgetTracker;
import com.ayaan.FinanceTracker.models.IncomeExpenseSources;

public class BudgetTrackerService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetTrackerService.class);

    IncomeService incomeImpl = new IncomeService();
    IncomeExpenseSourcesService incomeExpenseSourcesImpl = new IncomeExpenseSourcesService();
    IncomeExpenseSources incomeExpenseSources = new IncomeExpenseSources();
    IncomeExpenseSourcesDAOImpl incomeExpenseSourcesDAO = new IncomeExpenseSourcesDAOImpl();
    BudgetTrackerDAOImpl budgetTrackerDAO = new BudgetTrackerDAOImpl();
    IncomeDAOImpl incomeDAO = new IncomeDAOImpl();
    ExpenseDAOImpl expenseDAO = new ExpenseDAOImpl();
    AccountTransaction accountTransaction = new AccountTransaction();

    public void incomeOverview() {
        try {
            Scanner scanner = new Scanner(System.in);

            String source = null;
            IncomeExpenseSources incomeExpenseSources = null;
            while (incomeExpenseSources == null) {
                System.out.println("Income Source: ");
                source = scanner.nextLine();
                incomeExpenseSources = incomeExpenseSourcesDAO.getIncomeExpenseSourceByCondition(source);
                if (incomeExpenseSources == null) {
                    throw new DataAccessException("\nError: No Income Source found.");
                }
            }

            Double goal = null;
            while (goal == null) {
                System.out.println("Enter your Monthly Goal: ");
                goal = scanner.nextDouble();
                if (goal <= 0) {
                    System.out.println("Error: Monthly Goal must be a positive number.");
                    goal = null; // Reset to retry
                }
            }

            incomeExpenseSourcesImpl.monthlyGoal(source, goal);
            Double incomeValue = incomeDAO.getIncomeBySourceFromIncomes(source);
            System.out.println(incomeValue);

            Double remaining = goal - incomeValue;
            Double progress = (incomeValue / goal) * 100;

            BudgetTracker budgetTracker = new BudgetTracker(
                    null,
                    remaining,
                    null,
                    progress);

            if (budgetTracker != null) {
                logger.info("Your remaining amount is " + remaining);
                logger.info("Your progress is " + progress + "%");
                logger.info("Income Updated. ");
            }
        } catch (InputMismatchException e) {
            logger.error("Invalid input: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid income value: {}", e.getMessage());
        } catch (DataAccessException e) {
            logger.error("Database access error: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean setBudget(String name, String budget) throws SQLException, ExceedsPercentageException{
    
            Double totalPercentage = 0.0;
            for (BudgetTracker budgetTracker : budgetTrackerDAO.getAllBudgets()) {
                totalPercentage += budgetTracker.getBudgetPercentage();
                logger.info("No Budget Found! ");
            }
            
            double budgetValue =  Double.parseDouble(budget);
            if (totalPercentage + budgetValue > 100) {
                throw new ExceedsPercentageException("Total percentage exceeds 100%. Remaining percent bar = " +
                        (100 - totalPercentage) + "%");
            }

            BudgetTracker budgetTracker = new BudgetTracker(
                    name,
                    null,
                    Double.parseDouble(budget),
                    null);

            budgetTrackerDAO.saveBudget(budgetTracker);
            logger.info("Budget saved successfully! Remaining percentage: "
                    + (100 - totalPercentage - Double.parseDouble(budget)) + "%.");

        return false;
    }

    public void expenseOverview() {
        try {
            Scanner scanner = new Scanner(System.in);

            String expenseSource = null;
            IncomeExpenseSources incomeExpenseSources = null;
            while (incomeExpenseSources == null) {
                System.out.println("Expense Source: ");
                expenseSource = scanner.nextLine();
                incomeExpenseSources = incomeExpenseSourcesDAO.getIncomeExpenseSourceByCondition(expenseSource);
                if (incomeExpenseSources == null) {
                    throw new DataAccessException("\nError: No Income Source found.");
                }
            }

            Double budget = null;
            while (budget == null) {
                System.out.println("Enter your Monthly Budget: ");
                budget = scanner.nextDouble();
                if (budget <= 0) {
                    System.out.println("Error: Monthly Goal must be a positive number.");
                    budget = null; // Reset to retry
                }
            }

            incomeExpenseSourcesImpl.monthlyGoal(expenseSource, budget);
            Double expenseValue = expenseDAO.getExpenseBySourceFromExpense(expenseSource);
            if (expenseValue == null) {
                throw new DataAccessException("No expense Found!");
            }

            Double remaining = budget + expenseValue;
            expenseValue = Math.abs(expenseValue);
            Double progress = (expenseValue / budget) * 100;

            BudgetTracker budgetTracker2 = new BudgetTracker(
                    null,
                    remaining,
                    null,
                    progress);

            if (budgetTracker2 != null) {
                logger.info("Your remaining amount is " + remaining);
                logger.info("Your progress is " + progress + "%");
                logger.info("Expense Updated");
            }

        } catch (InputMismatchException e) {
            logger.error("Invalid input: {}", e.getMessage());
        } catch (DataAccessException e) {
            logger.error("Database access error: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void IncomeOverviewDisplay(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Object[]> budgetTracker = budgetTrackerDAO.displayIncome();
            if (budgetTracker == null || budgetTracker.isEmpty()) {
                throw new DataAccessException("No Income Found!");
            }
            
            request.setAttribute("budgetTrackerIncome", budgetTracker);
            
        } catch (DataAccessException e) {
            logger.error("Database access error: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while fetching bank transactions.");
            e.printStackTrace();
        }
    }

    public void budgetOverview(HttpServletRequest request, HttpServletResponse response) {
    	
    	 try {
             List<BudgetTracker> budgets = budgetTrackerDAO.getAllBudgets();
             List<Expense> expenses = expenseDAO.getAllExpense();
             List<IncomeExpenseSources> incomeExpenseSources = incomeExpenseSourcesDAO.getAllIncomeExpenseSource();

             Double totalIncome = incomeDAO.getIncomes();
             if (totalIncome == null) {
                 throw new DataAccessException("Total income is null.");
             }

             List<Map<String, Object>> budgetData = new ArrayList<>();
             for (BudgetTracker budget : budgets) {
                 Map<String, Object> budgetMap = new HashMap<>();
                 budgetMap.put("name", budget.getName());
                 budgetMap.put("budgetPercentage", budget.getBudgetPercentage());

                 Double allocatedAmount = (totalIncome * budget.getBudgetPercentage()) / 100;
                 budgetMap.put("allocatedAmount", allocatedAmount);

                 Double currentExpense = 0.0;
                 for (IncomeExpenseSources sources : incomeExpenseSources) {
                     if (sources.getBudgetTracker() != null &&
                             sources.getBudgetTracker().getBudgetTrackerId().equals(budget.getBudgetTrackerId())) {
                         for (Expense expense : expenses) {
                             if (expense.getExpenseSourceId().getIncomeExpenseSourceId()
                                     .equals(sources.getIncomeExpenseSourceId())) {
                                 currentExpense += expense.getExpense();
                             }
                         }
                     }
                 }

                 Double remaining = allocatedAmount + currentExpense;
                 
                 if(currentExpense < 0 ) {
                	 currentExpense = Math.abs(currentExpense);
                 }
                 
                 budgetMap.put("currentExpense", currentExpense);
                 budgetMap.put("remaining", remaining);

                 budgetData.add(budgetMap);
             }

             request.setAttribute("budgetData", budgetData);
             
         } catch (DataAccessException e) {
             request.setAttribute("errorMessage", "Database access error: " + e.getMessage());
//             request.getRequestDispatcher("/error.jsp").forward(request, response);
         } catch (Exception e) {
             request.setAttribute("errorMessage", "An error occurred while fetching budget data.");
//             request.getRequestDispatcher("/error.jsp").forward(request, response);
         }  	 
    }

    public void expenseOverviewDisplay(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Object[]> budgetTracker = budgetTrackerDAO.displayExpense();
            if (budgetTracker == null || budgetTracker.isEmpty()) {
                throw new DataAccessException("NO Expense Found!");
            }
            
            request.setAttribute("budgetTrackerExpense", budgetTracker);
            
        } catch (DataAccessException e) {
            logger.error("Database access error: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while fetching bank transactions.");
            e.printStackTrace();
        }
    }
}
