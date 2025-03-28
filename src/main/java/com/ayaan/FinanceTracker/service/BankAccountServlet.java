package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.InputMismatchException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayaan.FinanceTracker.dao.BankAccountDAO;
import com.ayaan.FinanceTracker.daoImpl.BankAccountDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.BankAlreadyExistException;
import com.ayaan.FinanceTracker.models.BankAccount;

/**
 * Servlet implementation class BankAccountServlet
 */
//@WebServlet("/BankAccountServlet")
public class BankAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BankAccountService bankAccountservice = new BankAccountService();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BankAccountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/BankAccount.jsp");
		dispatcher.forward(request, response);

		response.getWriter().append("Bank Servlet get is calling ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("bankName");
		
		try {
			bankAccountservice.addBankAccount(name);
			response.getWriter().append("Bank Account Added Successfully");
			request.getRequestDispatcher("DashboardServlet").forward(request, response);

		} catch (BankAlreadyExistException e) {
			response.getWriter().append("Error while giving bank Account");
			response.getWriter().append(e.getMessage());
		} catch (SQLException e) {
			response.getWriter().append("Failed to proceed");
		} catch (Exception e) {
			response.getWriter().append("An unexpected error occurred");
			e.printStackTrace();
		}

		// request.getRequestDispatcher("Dashboard.jsp").include(request, response);

//		BankAccount account = new BankAccount(name, new Date(System.currentTimeMillis()));
//		BankAccountDAO dao = new BankAccountDAOImpl();		
//		dao.saveBankAccount(account);

	}

}
