package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;

/**
 * Servlet implementation class ExpenseServlet
 */
//@WebServlet("/ExpenseServlet")
public class ExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ExpenseService expenseService = new ExpenseService();

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

		expenseService.listExpense(response);
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
			response.getWriter().append("Expense Added Successfully");

		} catch (DataAccessException e) {
			response.getWriter().append("Database error while fetching income sources");
			response.getWriter().append(e.getMessage());
		} catch (SQLException e) {
			response.getWriter().append("Failed to Proceed");
		} catch (Exception e) {
			response.getWriter().append("\nError: Invalid input");
			e.printStackTrace();
		}

	}

}
