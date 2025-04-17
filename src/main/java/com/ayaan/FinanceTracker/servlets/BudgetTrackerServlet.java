package com.ayaan.FinanceTracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayaan.FinanceTracker.daoImpl.BudgetTrackerDAOImpl;
import com.ayaan.FinanceTracker.dao.BudgetTrackerDAO;
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
		
		    BudgetTrackerDAO dao = new BudgetTrackerDAOImpl();
		    List<BudgetTracker> budgetList = dao.getAllBudgets(); 

		    response.setContentType("text/html");
		    PrintWriter out = response.getWriter();

		    for (BudgetTracker budget : budgetList) {
		        out.println("<option>" + budget.getName() + "</option>");
		    }
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
				response.getWriter().append("Budget Updated Successfully");
			}
			
			if(monthlyGoal != null && incomeSource != null) {
				budgetTrackerService.incomeOverview(incomeSource, monthlyGoal);
				response.getWriter().append("Monthly Goal Updated Successfully");
			}
			
			if(monthlyBudget != null && expenseSource != null) {
				budgetTrackerService.expenseOverview(expenseSource, monthlyBudget);
				response.getWriter().append("Monthly Budget Updated Successfully");
			}
			
			request.getRequestDispatcher("DashboardServlet").include(request, response);
			
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write("Database error occurred.");
		} catch (ExceedsPercentageException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write(e.getMessage());
		} catch (MonthlyGoalException e) {
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write(e.getMessage());
		} catch (MonthlyBudgetException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write(e.getMessage());
		} catch (Exception e) {
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write("Unexpected Error Occured");
			e.printStackTrace();
		}
	}
}
