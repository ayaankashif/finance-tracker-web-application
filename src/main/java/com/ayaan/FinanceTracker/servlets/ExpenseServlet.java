package com.ayaan.FinanceTracker.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.service.BankAccountService;
import com.ayaan.FinanceTracker.service.ExpenseService;
import com.ayaan.FinanceTracker.service.IncomeExpenseSourcesService;

/**
 * Servlet implementation class ExpenseServlet
 */
//@WebServlet("/ExpenseServlet")
public class ExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ExpenseService expenseService = new ExpenseService();
	IncomeExpenseSourcesService incomeExpenseSources = new IncomeExpenseSourcesService();
	BankAccountService bankAccountService = new BankAccountService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public ExpenseServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		expenseService.listExpense(response, request);
		incomeExpenseSources.listSources(response, request);
		bankAccountService.listBankAccount(request, response);
		request.getRequestDispatcher("Exp.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String bankAccount = request.getParameter("bankAccount");
		String expense = request.getParameter("expense");
		String expenseSource = request.getParameter("expenseSource");
		
		try {
			expenseService.addExpense(name, bankAccount, expense, expenseSource);
			response.setContentType("text/plain");
			response.getWriter().write("Expense Added Successfully.");

		} catch (DataAccessException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 response.getWriter().write(e.getMessage());
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write("Database error occurred.");
		} catch (Exception e) {
			 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 response.getWriter().write("Unexpected Error Occurred");
			 e.printStackTrace();
		}
	}
}
