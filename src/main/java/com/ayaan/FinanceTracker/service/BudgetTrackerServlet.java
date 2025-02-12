package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// TODO Auto-generated method stub
		String budget = request.getParameter("budget");
		String budgetPer = request.getParameter("budgetPer");

		try {
			budgetTrackerService.setBudget(budget, budgetPer);
			response.getWriter().append("Budget Added Successfully");

		} catch (SQLException e) {
			response.getWriter().append("Failed to Proceed");
		} catch (Exception e) {
			response.getWriter().append("Unexpected Error Occured");
			e.printStackTrace();
		}
	}
}
