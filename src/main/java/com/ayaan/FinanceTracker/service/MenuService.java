package com.ayaan.FinanceTracker.service;

import java.util.Scanner;

import com.ayaan.FinanceTracker.daoImpl.AccountTransactionDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.BankAccountDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.ExpenseManager;
import com.ayaan.FinanceTracker.exceptionHandling.invalidInputException;
import com.ayaan.FinanceTracker.models.Income;

public class MenuService {
    BankAccountService bankAccountImpl = new BankAccountService();
    AccountTransactionService accountTransactionImpl = new AccountTransactionService();
    IncomeService incomeImpl = new IncomeService();
    BankAccountDAOImpl bankAccountDAO = new BankAccountDAOImpl();
    ExpenseService expenseImpl = new ExpenseService();
    Income income = new Income();
    AccountTransactionDAOImpl accountTransactionDAO = new AccountTransactionDAOImpl();
    BudgetTrackerService budgetTrackerService = new BudgetTrackerService();

    public void financeMenu() {
        try {
            System.out.println("\nFinance Tracker\n ");
            System.out.println("1. Dashboard");
            System.out.println("2. Bank Account");
            System.out.println("3. Income ");
            System.out.println("4. Expense ");
            System.out.println("5. Budget Tracker");
            System.out.println("6. Exit");
            Scanner scanner = new Scanner(System.in);
            Integer choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    dashboard();
                    financeMenu();
                    break;
                case 2:
                    bankMenu();
                    financeMenu();
                    break;
                case 3:
                    incomeMenu();
                    financeMenu();
                    break;
                case 4:
                    expenseMenu();
                    financeMenu();
                    break;
                case 5:
                    budgetTracker();
                    financeMenu();
                    break;
                case 6:
                    System.out.println("Exiting the finance Tracker.");
                    break;
                default:
                    throw new invalidInputException("Incorrect choice, Try again!!!");
            }
        } catch (ExpenseManager e) {
            System.out.println(e.getMessage());
            financeMenu();
        }
    }

    public void bankMenu() {
        System.out.println("Bank Account Menu\n ");
        System.out.println("1. Add Bank Account");
        System.out.println("2. Bank Transactions");
        System.out.println("3. Update Bank Account");
        System.out.println("4. List Bank Account");
        System.out.println("5. Back to finance Tracker");
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();

        switch (input) {
            case 1:
                bankAccountImpl.addBankAccount();
                break;
            case 2:
                accountTransactionImpl.listBankTransaction();
                break;
            case 3:
                bankAccountImpl.updateBankAccount();
                break;
            case 4:
                bankAccountImpl.listBankAccount();
                break;
            case 5:
                financeMenu();
                break;
            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    public void incomeMenu() {
        System.out.println("Income Menu\n ");
        System.out.println("1. Add Income Source");
        System.out.println("2. Add Income");
        System.out.println("3. Update Income");
        System.out.println("4. List Income ");
        System.out.println("5. Back to finance Tracker");
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();

        switch (input) {
            case 1:
                incomeImpl.addIncomeSource();
                break;
            case 2:
                incomeImpl.addIncome();
                break;
            case 3:
                incomeImpl.updateIncome();
                break;
            case 4:
                incomeImpl.listIncome();
                break;
            case 5:
                financeMenu();
                break;
            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    public void expenseMenu() {
        System.out.println("Expense Menu\n ");
        System.out.println("1. Add Expense Source");
        System.out.println("2. Add Expense");
        System.out.println("3. Update Expense");
        System.out.println("4. List Expense");
        System.out.println("5. Back to finance Tracker");
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();

        switch (input) {
            case 1:
                expenseImpl.addExpenseSource();
                break;
            case 2:
                expenseImpl.addExpense();
                break;
            case 3:
                expenseImpl.updateExpense();
                break;
            case 4:
                expenseImpl.listExpense();
                break;
            case 5:
                financeMenu();
                break;
            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    public void dashboard() {
        System.out.println("\nDashboard:");
        System.out.println("1. Bank Stats");
        System.out.println("2. Monthly Credits");
        System.out.println("3. Monthly Debits");
        System.out.println("4. Back to finance Tracker");
        Scanner scanner = new Scanner(System.in);
        Integer choice = scanner.nextInt();

        switch (choice) {
            case 1:
                accountTransactionImpl.bankStats();
                break;
            case 2:
                accountTransactionImpl.monthlyCreditStats();
                break;
            case 3:
                accountTransactionImpl.monhtlyDebitStats();
                break;
            case 4:
                financeMenu();
                break;
            default:
                break;
        }
    }

    public void budgetTracker() {
        System.out.println("\nBudget Tracker:");
        System.out.println("1. Set Budget");
        System.out.println("2. Budget Overview");
        System.out.println("3. Income Overview");
        System.out.println("4. Expense Overview");
        System.out.println("5. Set your Monthly Goal");
        System.out.println("6. Set you monthly Budget");
        System.out.println("7. Back to finance Tracker");
        Scanner scanner = new Scanner(System.in);
        Integer choice = scanner.nextInt();

        switch (choice) {
            case 1:
                budgetTrackerService.setBudget();
                break;
            case 2:
                budgetTrackerService.budgetOverview();
                break;
            case 3:
                budgetTrackerService.IncomeOverviewDisplay();
                break;
            case 4:
                budgetTrackerService.expenseOverviewDisplay();
                break;
            case 5:
                budgetTrackerService.incomeOverview();
                break;
            case 6:
                budgetTrackerService.expenseOverview();
                break;
            case 7:
                financeMenu();
                break;
            default:
                break;
        }
    }

}