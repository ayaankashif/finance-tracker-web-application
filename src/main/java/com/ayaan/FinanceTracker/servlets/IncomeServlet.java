package com.ayaan.FinanceTracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayaan.FinanceTracker.dao.AccountTransactionDAO;
import com.ayaan.FinanceTracker.dao.BankAccountDAO;
import com.ayaan.FinanceTracker.dao.IncomeDAO;
import com.ayaan.FinanceTracker.dao.IncomeExpenseSourcesDAO;
import com.ayaan.FinanceTracker.daoImpl.IncomeDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.IncomeExpenseSourcesDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.AccountTransactionDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.BankAccountDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.exceptionHandling.ExceedsPercentageException;
import com.ayaan.FinanceTracker.models.AccountTransaction;
import com.ayaan.FinanceTracker.models.BankAccount;
import com.ayaan.FinanceTracker.models.Income;
import com.ayaan.FinanceTracker.models.IncomeExpenseSources;
import com.ayaan.FinanceTracker.service.BankAccountService;
import com.ayaan.FinanceTracker.service.IncomeExpenseSourcesService;
import com.ayaan.FinanceTracker.service.IncomeService;

/**
 * Servlet implementation class IncomeServlet
 */

//@WebServlet("/IncomeServlet")
public class IncomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IncomeDAO incomeDAO = new IncomeDAOImpl();
	IncomeService incomeService = new IncomeService();
	IncomeExpenseSourcesDAO incomeExpenseSourcesDAO = new IncomeExpenseSourcesDAOImpl();
	BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();
	IncomeExpenseSourcesService incomeExpenseSources = new IncomeExpenseSourcesService();
	BankAccountService bankAccountService = new BankAccountService();

	/**
	 * Default constructor.
	 */

	public IncomeServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		incomeService.listIncome(request, response);
		incomeExpenseSources.listSources(response, request);
		bankAccountService.listBankAccount(request, response);
		request.getRequestDispatcher("Inc.jsp").forward(request, response);
		
//		response.getWriter().append("Served at: ").append(request.getContextPath());

//		request.dispatcher("https://www.google.com")

//		RequestDispatcher dispatcher = request.getRequestDispatcher("Inc.jsp");
//		response.getWriter().append("get request");
//		dispatcher.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String bankAccount = request.getParameter("bankAccount");
		String income = request.getParameter("income");
		String incomeSources = request.getParameter("incomeSource");
		
		try {
			incomeService.addIncome(name, bankAccount, income, incomeSources);
			response.setContentType("text/plain");
			response.getWriter().write("Income Added Successfuly.");
		    
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write("Database error occurred.");
		} catch(DataAccessException e) {
			 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 response.getWriter().write(e.getMessage());
		} catch (Exception e) {
			 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 response.getWriter().write("Unexpected Error Occurred");
		}
}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String account = request.getParameter("bankAccount");
		String incomeSource = request.getParameter("incomeSource");
		String income = request.getParameter("income");
	
		response.getWriter().append("DoPOST is calling " + name + " " + account + " " + incomeSource + " " + income);
	}
}
