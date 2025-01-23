package com.ayaan.FinanceTracker.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayaan.FinanceTracker.dao.AccountTransactionDAO;
import com.ayaan.FinanceTracker.daoImpl.AccountTransactionDAOImpl;
import com.ayaan.FinanceTracker.models.AccountTransaction;

/**
 * Servlet implementation class IncomeServlet
 */

@WebServlet("/IncomeServlet")
public class IncomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	
    public IncomeServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String account = request.getParameter("bankAccount");
		String incomeSource = request.getParameter("incomeSource");
		String income = request.getParameter("income");
		
		AccountTransactionDAO accountDAO = new AccountTransactionDAOImpl();
		AccountTransaction accountIMPL = new AccountTransaction(null, "debit", 200.00 , null);
		accountDAO.saveTransaction(accountIMPL);	
		
		System.out.println("DB is working");
		
		response.getWriter().append("DoPOST is calling " + name + " " + account + " " + incomeSource + " " + income);
		
	}

}
