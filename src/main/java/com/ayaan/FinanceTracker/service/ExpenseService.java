package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaan.FinanceTracker.models.BudgetTracker;
import com.ayaan.FinanceTracker.daoImpl.BankAccountDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.BudgetTrackerDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.ExpenseDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.IncomeExpenseSourcesDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.exceptionHandling.InvalidIDException;
import com.ayaan.FinanceTracker.models.AccountTransaction;
import com.ayaan.FinanceTracker.models.BankAccount;
import com.ayaan.FinanceTracker.models.Expense;
import com.ayaan.FinanceTracker.models.Income;
import com.ayaan.FinanceTracker.models.IncomeExpenseSources;

public class ExpenseService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    IncomeExpenseSourcesDAOImpl incomeExpenseSourcesDAO = new IncomeExpenseSourcesDAOImpl();
    IncomeExpenseSourcesService incomeExpenseSourcesImpl = new IncomeExpenseSourcesService();
    BankAccountDAOImpl bankAccountDAO = new BankAccountDAOImpl();
    AccountTransactionService accountTransactionImpl = new AccountTransactionService();
    ExpenseDAOImpl expenseDAO = new ExpenseDAOImpl();
    AccountTransaction accountTransaction = new AccountTransaction();
    BudgetTrackerDAOImpl budgetTrackerDAO = new BudgetTrackerDAOImpl();

    public boolean addExpense(String name, String bankAccountName, String expense, String expenseSource) throws DataAccessException, SQLException  {
       
        	double expenseValue = Double.parseDouble(expense);
        	
            if (expenseValue > 0) {
            	expenseValue = -expenseValue;
            }
          
            BankAccount bankAccount = null;
                bankAccount = bankAccountDAO.getBankAccountByCondition(bankAccountName);
                if (bankAccount == null) {
                    throw new DataAccessException(
                            "\nError: No Bank account found. Please enter a valid bank account name.");
                }

            IncomeExpenseSources incomeExpenseSources = null;
                incomeExpenseSources = incomeExpenseSourcesDAO.getIncomeExpenseSourceByCondition(expenseSource);
                if (incomeExpenseSources == null) {
                    throw new DataAccessException("No Expense source found.");
                }

            Expense expense1 = new Expense(name, bankAccount, expenseValue, incomeExpenseSources,
                    new Date(System.currentTimeMillis()));

            accountTransactionImpl.addTransaction(bankAccount, "Debit", expenseValue);
            accountTransaction.setTransactionAmt(accountTransaction.getTransactionAmt() + expenseValue);

            expenseDAO.saveExpense(expense1);
            logger.info("\nExpense added successfully");

        return false;
    }

    public void updateExpense() {
        try {
            System.out.println("Name: ");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();

            BankAccount bankAccount = null;
            while (bankAccount == null) {
                System.out.println("Bank Account: ");
                String bankAcc = scanner.nextLine();
                bankAccount = bankAccountDAO.getBankAccountByCondition(bankAcc);
                if (bankAccount == null) {
                    throw new DataAccessException(" No bank Account found.");
                }
            }

            System.out.println("Expense");
            Double exp = scanner.nextDouble();
            if (exp > 0) {
                exp = -exp;
            }

            IncomeExpenseSources incomeExpenseSources = null;
            while (incomeExpenseSources == null) {
                System.out.println("Expense Source: ");
                String source = scanner.nextLine();
                incomeExpenseSources = incomeExpenseSourcesDAO.getIncomeExpenseSourceByCondition(source);
                if (incomeExpenseSources == null) {
                    logger.info("No Expense Source Found!");
                }
            }

            System.out.println("Expense ID: ");
            Integer id = scanner.nextInt();
            Expense expense1 = expenseDAO.getExpensebyId(id);

            if (!expense1.getName().equalsIgnoreCase(name)) {
                throw new InvalidIDException("The ID doesn't match your records. Aborting update");
            }

            System.out.println("\nPress '1' to add income into your existing amount.\nPress '2' to Rectify your actual amount");
            Integer choice = scanner.nextInt();

            if (choice == 1) {
                Double updatedExpense = expense1.getExpense() + exp;
                expense1.setExpense(updatedExpense);
                logger.info("\nUpdated Total Expense: " + updatedExpense);
                accountTransactionImpl.addTransaction(bankAccount, "Debit", exp);
                accountTransaction.setTransactionAmt(accountTransaction.getTransactionAmt() + exp);
                expenseDAO.updateExpense(expense1);

            } else if (choice == 2) {
                Expense expense2 = new Expense(id, name, bankAccount, exp, incomeExpenseSources,
                        new Date(System.currentTimeMillis()));
                        
                expenseDAO.updateExpense(expense2);
                logger.info("\nExpense Updated.");
            }
        } catch (InvalidIDException e) {
            logger.error("No ID found: {}", e.getMessage());
        } catch (DataAccessException e) {
            logger.error("Database error while fetching income sources: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError: Invalid input");
            e.printStackTrace();
        }
    }

    public boolean addExpenseSource(String expenseSource, String budget) throws DataAccessException, SQLException{
            BudgetTracker budgetTracker = null;
                budgetTracker = budgetTrackerDAO.getBudgetByCondition(budget);
                if (budgetTracker == null) {
                    throw new DataAccessException("Error, no budget found ");
                }
            incomeExpenseSourcesImpl.addIncomeExpenseSource(expenseSource, 'E', budgetTracker);

        return false;
    }

    public void listExpense(HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            List<Expense> expenses = expenseDAO.getAllExpense();
            if (expenses == null) {
                throw new DataAccessException("No Expense Found!");
            }
            
            List<Expense> expense = expenses;
            
            request.setAttribute("expense", expense);
            request.setAttribute("expenses", expenses);

        } catch (DataAccessException e) {
            logger.error("Database error while fetching income sources: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println("No Expense Found");
            e.printStackTrace();
        }
    }
}