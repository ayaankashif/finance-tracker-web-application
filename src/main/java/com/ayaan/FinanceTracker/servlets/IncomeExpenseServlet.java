package com.ayaan.FinanceTracker.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.service.ExpenseService;
import com.ayaan.FinanceTracker.service.IncomeExpenseSourcesService;
import com.ayaan.FinanceTracker.service.IncomeService;

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
		
		incomeExpenseSources.listSources(response, request);
		request.getRequestDispatcher("IncExpSource.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String incomeSource = request.getParameter("incomeSrc");
		String expenseSource = request.getParameter("expenseSrc");
		String budgetTracker = request.getParameter("budgetTracker");
		
		try {
			
			if(incomeSource != null) {
				incomeService.addIncomeSource(incomeSource);				
			}
			
			if(expenseSource != null && budgetTracker != null) {
				expenseService.addExpenseSource(expenseSource, budgetTracker);
			}
			
			response.getWriter().append("Source Added Successfully");
			request.getRequestDispatcher("DashboardServlet").forward(request, response);

		} catch (DataAccessException e) {
			response.getWriter().append("Unexpected Error Occured");
			response.getWriter().append(e.getMessage());
		} catch (SQLException e) {
			response.getWriter().append("Failed to Proceed");
		} catch (Exception e) {
			response.getWriter().append("\nError: Invalid input");
			e.printStackTrace();
		}	
	}
}
