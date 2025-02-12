package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IncomeExpenseServlet
 */
//@WebServlet("/IncomeExpenseServlet")
public class IncomeExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IncomeService incomeService = new IncomeService();
	ExpenseService expenseService = new ExpenseService();
	IncomeExpenseSourcesService incomeExpenseSources = new IncomeExpenseSourcesService();
          /**
     * @see HttpServlet#HttpServlet()
     */
    public IncomeExpenseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		incomeExpenseSources.listSources(response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String incomeSource = request.getParameter("incomeSrc");
		String expenseSource = request.getParameter("expenseSrc");
		String budgetTracker = request.getParameter("budgetTracker");
		
		boolean isSaved = incomeService.addIncomeSource(incomeSource);
		boolean isSavedExp = expenseService.addExpenseSource(expenseSource, budgetTracker);
		
		if(isSaved) {
			response.getWriter().append("Income Source Added Successfully.");
		} else if(isSavedExp) {
			response.getWriter().append("Expense Source Added Successfully.");
		} else {
			response.getWriter().append("Failed to Add Source.");
		}
		
		
		response.getWriter().append("DoPOST is calling " + incomeSource + " " + expenseSource);
				
	}

}
