package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaan.FinanceTracker.dao.IncomeDAO;
import com.ayaan.FinanceTracker.daoImpl.BankAccountDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.IncomeDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.IncomeExpenseSourcesDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.exceptionHandling.InvalidIDException;
import com.ayaan.FinanceTracker.exceptionHandling.invalidInputException;
import com.ayaan.FinanceTracker.models.AccountTransaction;
import com.ayaan.FinanceTracker.models.BankAccount;
import com.ayaan.FinanceTracker.models.Income;
import com.ayaan.FinanceTracker.models.IncomeExpenseSources;

public class IncomeService {
	private static final Logger logger = LoggerFactory.getLogger(IncomeService.class);

	IncomeDAO incomeDAO = new IncomeDAOImpl();
	BankAccountDAOImpl bankAccountDAO = new BankAccountDAOImpl();
	AccountTransactionService accountTransactionImpl = new AccountTransactionService();
	IncomeExpenseSourcesService incomeExpenseSourcesImpl = new IncomeExpenseSourcesService();
	IncomeExpenseSourcesDAOImpl incomeExpenseSourcesDAO = new IncomeExpenseSourcesDAOImpl();
	AccountTransaction accountTransaction = new AccountTransaction();
	IncomeExpenseSources incomeExpenseSources = new IncomeExpenseSources();

	public boolean addIncome(String name, String bankAccountName, String income, String incomeSources) {
		try {

			BankAccount bankAccount = null;
			bankAccount = bankAccountDAO.getBankAccountByCondition(bankAccountName);
			if (bankAccount == null) {
				throw new DataAccessException(
						"\nError: No Bank account found. Please enter a valid bank account name.");
			}

			IncomeExpenseSources incomeExpenseSources = null;
			incomeExpenseSources = incomeExpenseSourcesDAO.getIncomeExpenseSourceByCondition(incomeSources);
			if (incomeExpenseSources == null) {
				throw new DataAccessException("\nError: No Income Source found.");
			}

			accountTransaction.setTransactionAmt(accountTransaction.getTransactionAmt() + Double.parseDouble(income));

			Income income1 = new Income(name, bankAccount, Double.parseDouble(income), incomeExpenseSources,
					new Date(System.currentTimeMillis()));

			accountTransactionImpl.addTransaction(bankAccount, "Credit", Double.parseDouble(income));

			return incomeDAO.saveIncome(income1);

		} catch (IllegalArgumentException e) {
			logger.error("Invalid income value: {}", e.getMessage());
		} catch (DataAccessException e) {
			logger.error("Database access error: {}", e.getMessage());
		} catch (Exception e) {
			logger.error("An unexpected error occurred: {}", e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public void updateIncome(String name, String bankAccountName, String Income, String incomeSource) {
		try {
			Scanner scanner = new Scanner(System.in);
				BankAccount bankAccount = null;
				bankAccount = bankAccountDAO.getBankAccountByCondition(bankAccountName);
				if (bankAccount == null) {
					throw new DataAccessException(
							"\nError: No Bank account found. Please enter a valid bank account name.");
				}

			System.out.println("Income: ");
			Double income = scanner.nextDouble();

			IncomeExpenseSources incomeExpenseSources = null;
				incomeExpenseSources = incomeExpenseSourcesDAO.getIncomeExpenseSourceByCondition(incomeSource);
				if (incomeExpenseSources == null) {
					logger.info("No Income Source Found!");
				}
			

			System.out.println("Income ID: ");
			Integer id = scanner.nextInt();
			Income existingIncome = incomeDAO.getIncomebyId(id);

			if (!existingIncome.getName().equalsIgnoreCase(name)) {
				throw new InvalidIDException("The ID doesn't match your records. Aborting update");
			}

			System.out.println(
					"\nPress '1' to add income into your existing amount.\nPress '2' to Rectify your actual amount");
			Integer choice = scanner.nextInt();

			if (choice == 1) {
				Double updatedAmount = existingIncome.getIncome() + income;
				existingIncome.setIncome(updatedAmount);
				logger.info("Updated Total Income: " + updatedAmount);
				accountTransaction.setTransactionAmt(accountTransaction.getTransactionAmt() + income);
				accountTransactionImpl.addTransaction(bankAccount, "Credit", income);
				incomeDAO.updateIncome(existingIncome);

			} else if (choice == 2) {
				Income income1 = new Income(id, name, bankAccount, income, incomeExpenseSources,
						new Date(System.currentTimeMillis()));

				incomeDAO.updateIncome(income1);
			} else {
				throw new invalidInputException("Invalid choice ");
			}
		} catch (invalidInputException e) {
			logger.error("Invalid input found: {}", e.getMessage());
		} catch (InvalidIDException e) {
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

	public boolean addIncomeSource(String incomeSource) {
		try {
			incomeExpenseSourcesImpl.addIncomeExpenseSource(incomeSource, 'I', null);
			logger.info("Income Source Added");

		} catch (IllegalArgumentException e) {
			logger.error("Invalid income source: {}", e.getMessage());
		} catch (Exception e) {
			logger.error("An unexpected error occurred: {}", e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	

	public void listIncome(HttpServletResponse response) throws IOException {
    	
    	response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
   		try {
   			List<Income> incomes = incomeDAO.getAllIncome();
   			if (incomes == null) {
   				throw new DataAccessException("NO Incomes Found");
   			}

    		out.println("<html><head><title>Income List</title></head><body>");
    		out.println("<html><head><link href = \"Style.css\" rel = \"stylesheet\"></head><body>");
    		out.println("<h1>Incomes List:</h1>");
    		out.println("<table border='1'>");
    		out.println("<tr><th>Income ID</th><th>Name</th><th>Income Source</th><th>Income</th></tr>");

    		for (Income income : incomes) {
    			out.println("<tr>");
    			out.println("<td>" + income.getIncomeId() + "</td>");
    			out.println("<td>" + income.getName() + "</td>");
    			out.println("<td>" + income.getIncomeSources().getIncomeExpenseSource() + "</td>");
    			out.println("<td>" + income.getIncome() + "</td>");
    			out.println("</tr>");
    		}
    			out.println("</table>");
    			out.println("</body></html>");

    		} catch( DataAccessException e) {
    				out.println("<h1>Error: " + e.getMessage() + "</h1>");
    				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		} catch( Exception e) {
    			out.println("<h1>An unexpected error occurred.</h1>");
    			e.printStackTrace(out);
    			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		} finally {
    			out.close();
    		}
	}
}