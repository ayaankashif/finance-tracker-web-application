package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ayaan.FinanceTracker.daoImpl.AccountTransactionDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.BudgetTrackerDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.ExpenseDAOImpl;
import com.ayaan.FinanceTracker.dao.ExpenseDAO;
import com.ayaan.FinanceTracker.daoImpl.IncomeDAOImpl;
import com.ayaan.FinanceTracker.daoImpl.IncomeExpenseSourcesDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.models.BudgetTracker;
import com.ayaan.FinanceTracker.models.Expense;
import com.ayaan.FinanceTracker.models.IncomeExpenseSources;
import com.ayaan.FinanceTracker.dao.AccountTransactionDAO;

/**
 * Servlet implementation class DashboardServlet
 */
//@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static final Logger logger = LoggerFactory.getLogger(BudgetTrackerService.class);
	BudgetTrackerService budgetTrackerService = new BudgetTrackerService();
	IncomeService incomeService = new IncomeService();
	ExpenseService expenseService = new ExpenseService();
	IncomeExpenseSourcesService incomeExpenseSources = new IncomeExpenseSourcesService();
	AccountTransactionDAO accountDAO = new AccountTransactionDAOImpl();
	ExpenseDAO expenseDAO = new ExpenseDAOImpl();
	BankAccountService bankAccountService = new BankAccountService();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		  budgetTrackerService.expenseOverviewDisplay(request, response);
		  budgetTrackerService.IncomeOverviewDisplay(request, response);
		  budgetTrackerService.budgetOverview(request, response);
		  incomeService.listIncome(request, response);
		  expenseService.listExpense(response, request);
		  incomeExpenseSources.listSources(response, request);
		  bankAccountService.listBankAccount(request, response);
		  Double totalAmount = accountDAO.getTotalBankBalance();
		  Double totalExpense = expenseDAO.getTotalExpense();		
		  
		  if(totalExpense < 0 ) {
			  totalExpense = Math.abs(totalExpense);
		  }
		  
		  request.setAttribute("totalExpense", totalExpense);
		  request.setAttribute("totalBalance", totalAmount);
		  LocalDate myObj = LocalDate.now();
		  request.setAttribute("date", myObj);
		  
		  request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
