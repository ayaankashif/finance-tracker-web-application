package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayaan.FinanceTracker.dao.BankAccountDAO;
import com.ayaan.FinanceTracker.daoImpl.BankAccountDAOImpl;
import com.ayaan.FinanceTracker.models.BankAccount;

/**
 * Servlet implementation class BankAccountServlet
 */
//@WebServlet("/BankAccountServlet")
public class BankAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BankAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/BankAccount.jsp");
		dispatcher.forward(request, response);
		
		response.getWriter().append("Bank Servlet get is calling ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		String name = request.getParameter("name");
		response.setContentType("text/html");
		response.getWriter().append("Bank Servlet Post is calling " + name);
			
		request.getRequestDispatcher("Dashboard.jsp").include(request, response);
		
		
		
//		BankAccount account = new BankAccount(name, new Date(System.currentTimeMillis()));
//		BankAccountDAO dao = new BankAccountDAOImpl();		
//		dao.saveBankAccount(account);
		
		
		
		
		
		
	}

}
