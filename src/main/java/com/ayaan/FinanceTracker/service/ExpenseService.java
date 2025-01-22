package com.ayaan.FinanceTracker.service;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
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

    public void addExpense() {
        try {
            System.out.println("Name:");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();

            System.out.println("Expense:");
            Double expense = scanner.nextDouble();
            if (expense > 0) {
                expense = -expense;
            }

            BankAccount bankAccount = null;
            while (bankAccount == null) {
                System.out.println("Bank Account:");
                String bankAcc = scanner.nextLine();
                bankAccount = bankAccountDAO.getBankAccountByCondition(bankAcc);
                if (bankAccount == null) {
                    throw new DataAccessException(
                            "\nError: No Bank account found. Please enter a valid bank account name.");
                }
            }

            IncomeExpenseSources incomeExpenseSources = null;
            while (incomeExpenseSources == null) {
                System.out.println("Expense Source:");
                String expenseSource = scanner.nextLine();
                incomeExpenseSources = incomeExpenseSourcesDAO.getIncomeExpenseSourceByCondition(expenseSource);
                if (incomeExpenseSources == null) {
                    throw new DataAccessException("No Expense source found.");
                }
            }

            Expense expense1 = new Expense(name, bankAccount, expense, incomeExpenseSources,
                    new Date(System.currentTimeMillis()));

            accountTransactionImpl.addTransaction(bankAccount, "Debit", expense);
            accountTransaction.setTransactionAmt(accountTransaction.getTransactionAmt() + expense);

            expenseDAO.saveExpense(expense1);
            logger.info("\nExpense added successfully");

        } catch (DataAccessException e) {
            logger.error("Database error while fetching income sources: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError: Invalid input");
            e.printStackTrace();
        }
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
                    throw new DataAccessException(" No Expense Source found.");
                }
            }

            System.out.println("Expense ID: ");
            Integer id = scanner.nextInt();
            Expense ex = expenseDAO.getExpensebyId(id);
            if (ex == null) {
                logger.info("No expense with this ID");
                return;
            }

            if (!ex.getName().equalsIgnoreCase(name)) {
                throw new InvalidIDException("The ID doesn't match your records. Aborting update");
            }

            Double updatedExpense = ex.getExpense() + exp;
            ex.setExpense(updatedExpense);

            Expense expense = new Expense(id, name, bankAccount, updatedExpense, incomeExpenseSources,
                    new Date(System.currentTimeMillis()));

            accountTransactionImpl.addTransaction(bankAccount, "Debit", exp);
            accountTransaction.setTransactionAmt(accountTransaction.getTransactionAmt() + exp);

            expenseDAO.updateExpense(expense);

        } catch (InvalidIDException e) {
            logger.error("No ID found: {}", e.getMessage());
        } catch (DataAccessException e) {
            logger.error("Database error while fetching income sources: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError: Invalid input");
            e.printStackTrace();
        }
    }

    public void addExpenseSource() {
        try {
            System.out.println("Expense Source:");
            Scanner scanner = new Scanner(System.in);
            String expenseSource = scanner.nextLine();

            BudgetTracker budgetTracker = null;
            while (budgetTracker == null) {
                System.out.println("Enter your budget Name to link this source: ");
                String name = scanner.nextLine();
                budgetTracker = budgetTrackerDAO.getBudgetByCondition(name);
                if (budgetTracker == null) {
                    throw new DataAccessException("Error, no budget found ");
                }
            }

            incomeExpenseSourcesImpl.addIncomeExpenseSource(expenseSource, 'E', budgetTracker);

        } catch (DataAccessException e) {
            logger.error("Database error while fetching income sources: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError: Invalid input");
            e.printStackTrace();
        }
    }

    public void listExpense() {
        try {
            List<Expense> expenses = expenseDAO.getAllExpense();
            if (expenses == null) {
                throw new DataAccessException("No Expense Found!");
            }

            System.out.println("\nExpenses List: ");
            System.out.printf("%-12s %-17s %-15s %-15s%n",
                    "Expense ID", "Name", "Expense Source", "Expense");
            System.out.println("-----------------------------------------------------------");

            expenses.forEach(expense -> System.out.printf("%-12s %-17s %-15s %-15s%n",
                    expense.getExpenseId(),
                    expense.getName(),
                    expense.getExpenseSourceId().getIncomeExpenseSource(),
                    expense.getExpense()));

        } catch (DataAccessException e) {
            logger.error("Database error while fetching income sources: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println("No Expense Found");
            e.printStackTrace();
        }
    }
}