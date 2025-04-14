package com.ayaan.FinanceTracker.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.exceptionHandling.ExceedsPercentageException;
import com.ayaan.FinanceTracker.exceptionHandling.MonthlyBudgetException;
import com.ayaan.FinanceTracker.exceptionHandling.MonthlyGoalException;
import com.ayaan.FinanceTracker.models.BudgetTracker;
import com.ayaan.FinanceTracker.models.Expense;
import com.ayaan.FinanceTracker.models.IncomeExpenseSources;
import com.ayaan.FinanceTracker.service.BudgetTrackerService;

/**
 * Servlet implementation class BudgetTrackerServlet
 */
//@WebServlet("/BudgetTrackerServlet")
public class BudgetTrackerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BudgetTrackerService budgetTrackerService = new BudgetTrackerService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BudgetTrackerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String budget = request.getParameter("budget");
		String budgetPer = request.getParameter("budgetPer");
		String incomeSource = request.getParameter("incomeSource");
		String expenseSource = request.getParameter("expenseSource");
		String monthlyGoal = request.getParameter("monthlyGoal");
		String monthlyBudget = request.getParameter("monthlyBudget");
		
		try {
			
			if(budget != null && budgetPer != null) {
				budgetTrackerService.setBudget(budget, budgetPer);
			}
			
			if(monthlyGoal != null && incomeSource != null) {
				budgetTrackerService.incomeOverview(incomeSource, monthlyGoal);
				response.getWriter().append("Budget Updated Successfully");
			}
			
			if(monthlyBudget != null && expenseSource != null) {
				budgetTrackerService.expenseOverview(expenseSource, monthlyBudget);
			}
			
			request.getRequestDispatcher("DashboardServlet").include(request, response);
			
		} catch (SQLException e) {
			request.setAttribute("errorMessage","Failed to proceed!");
		    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		    rd.forward(request, response);
		} catch (ExceedsPercentageException e) {
			request.setAttribute("errorMessage",e.getMessage());
		    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		    rd.forward(request, response);
		} catch (MonthlyGoalException e) {
			request.setAttribute("errorMessage","Unexpected Error Occured ");
			request.setAttribute("errorMessage",e.getMessage());
		    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		    rd.forward(request, response);
		} catch (MonthlyBudgetException e) {
			request.setAttribute("errorMessage","Unexpected Error Occured ");
			request.setAttribute("errorMessage",e.getMessage());
		    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		    rd.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage","An Error Occured");
		    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		    rd.forward(request, response);
			e.printStackTrace();
		}
	}
}
