package com.ayaan.FinanceTracker.service;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaan.FinanceTracker.daoImpl.BankAccountDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.IncomeDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.IncomeExpenseSourcesDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.exceptionHandling.InvalidIDException;
import com.ayaan.FinanceTracker.models.AccountTransaction;
import com.ayaan.FinanceTracker.models.BankAccount;
import com.ayaan.FinanceTracker.models.Income;
import com.ayaan.FinanceTracker.models.IncomeExpenseSources;

public class IncomeService {
    private static final Logger logger = LoggerFactory.getLogger(IncomeService.class);

    IncomeDAOImpl incomeDAO = new IncomeDAOImpl();
    BankAccountDAOImpl bankAccountDAO = new BankAccountDAOImpl();
    AccountTransactionService accountTransactionImpl = new AccountTransactionService();
    IncomeExpenseSourcesService incomeExpenseSourcesImpl = new IncomeExpenseSourcesService();
    IncomeExpenseSourcesDAOImpl incomeExpenseSourcesDAO = new IncomeExpenseSourcesDAOImpl();
    AccountTransaction accountTransaction = new AccountTransaction();
    IncomeExpenseSources incomeExpenseSources = new IncomeExpenseSources();

    public void addIncome() {
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
                    throw new DataAccessException(
                            "\nError: No Bank account found. Please enter a valid bank account name.");
                }
            }

            IncomeExpenseSources incomeExpenseSources = null;
            while (incomeExpenseSources == null) {
                System.out.println("Income Source: ");
                String incomeSource = scanner.nextLine();
                incomeExpenseSources = incomeExpenseSourcesDAO.getIncomeExpenseSourceByCondition(incomeSource);
                if (incomeExpenseSources == null) {
                    throw new DataAccessException("\nError: No Income Source found.");
                }
            }

            System.out.println("Income: ");
            Double income = scanner.nextDouble();
            accountTransaction.setTransactionAmt(accountTransaction.getTransactionAmt() + income);

            Income income1 = new Income(name, bankAccount, income, incomeExpenseSources,
                    new Date(System.currentTimeMillis()));
            accountTransactionImpl.addTransaction(bankAccount, "Credit", income);

            incomeDAO.saveIncome(income1);
            logger.info("\nIncome added successfully");

        } catch (IllegalArgumentException e) {
            logger.error("Invalid income value: {}", e.getMessage());
        } catch (DataAccessException e) {
            logger.error("Database access error: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateIncome() {
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
                    throw new DataAccessException(
                            "\nError: No Bank account found. Please enter a valid bank account name.");
                }
            }

            System.out.println("Income: ");
            Double income = scanner.nextDouble();

            IncomeExpenseSources incomeExpenseSources = null;
            while (incomeExpenseSources == null) {
                System.out.println("Income Source: ");
                String incomeSource = scanner.nextLine();
                incomeExpenseSources = incomeExpenseSourcesDAO.getIncomeExpenseSourceByCondition(incomeSource);
                if (incomeExpenseSources == null) {
                    throw new DataAccessException("\nError: No Income Source found.");
                }
            }

            System.out.println("Income ID: ");
            Integer id = scanner.nextInt();
            Income existingIncome = incomeDAO.getIncomebyId(id);
            if (existingIncome == null) {
                logger.info("No Income found with this ID");
                return;
            }

            if (!existingIncome.getName().equalsIgnoreCase(name)) {
                throw new InvalidIDException("The ID doesn't match your records. Aborting update");
            }

            Double updatedAmount = existingIncome.getIncome() + income;
            existingIncome.setIncome(updatedAmount);

            logger.info("Updated Total Income: " + updatedAmount);

            Income income1 = new Income(id, name, bankAccount, updatedAmount, incomeExpenseSources,
                    new Date(System.currentTimeMillis()));

            accountTransaction.setTransactionAmt(accountTransaction.getTransactionAmt() + income);
            accountTransactionImpl.addTransaction(bankAccount, "Credit", income);

            incomeDAO.updateIncome(income1);

        }catch (InvalidIDException e) {
            logger.error("No ID found: {}", e.getMessage());
        } catch (NoSuchElementException e) {
            logger.error("No matching element found: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid data provided: {}", e.getMessage());
        } catch (DataAccessException e) {
            logger.error("Error accessing the database: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void addIncomeSource() {
        try {
            System.out.println("Income Source: ");
            Scanner scanner = new Scanner(System.in);
            String incomeSource = scanner.nextLine();

            incomeExpenseSourcesImpl.addIncomeExpenseSource(incomeSource, 'I', null);
            logger.info("Income Source Added");

        } catch (IllegalArgumentException e) {
            logger.error("Invalid income source: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void listIncome() {
        try {
            List<Income> incomes = incomeDAO.getAllIncome();
            if (incomes == null) {
                throw new DataAccessException("NO Incomes Found");
            }
            
            System.out.println("\nIncomes List: ");
            System.out.printf("%-12s %-17s %-15s %-15s%n",
                    "Income ID", "Name", "Income Source", "Income");
            System.out.println("---------------------------------------------------");
            incomes.forEach(income -> System.out.printf("%-12s %-17s %-15s %-15s%n",
                    income.getIncomeId(),
                    income.getName(),
                    income.getIncomeSources().getIncomeExpenseSource(),
                    income.getIncome()));

        } catch(DataAccessException e){  
            logger.error("Database error while fetching income sources: {}", e.getMessage());
        } catch (Exception e) {
            logger.info("An unexpected error occurred.");
            e.printStackTrace();
        }
    }
}
