package com.ayaan.FinanceTracker.service;

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
			response.getWriter().append("Income Added Successfully");
			request.getRequestDispatcher("index.jsp").forward(request, response);

		} catch (SQLException e) {
			response.getWriter().append("Failed to Proceed");
			request.getRequestDispatcher("Inc.jsp").include(request, response);
		} catch(DataAccessException e) {
			response.getWriter().append("Unexpected Error Occured");	
			response.getWriter().append(e.getMessage());
			request.getRequestDispatcher("Inc.jsp").include(request, response);
			PrintWriter out = response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			
		} catch (Exception e) {
			response.getWriter().append("An Error Occured");
			e.printStackTrace();
		}
		
	
		/*
		 * }else { RequestDispatcher rd =
		 * getServletContext().getRequestDispatcher("sign-in.jsp"); 
		 * 
		 * }
		 */

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
