package com.ayaan.FinanceTracker.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayaan.FinanceTracker.daoImpl.UserDAOImpl;
import com.ayaan.FinanceTracker.dao.UserDAO;
import com.ayaan.FinanceTracker.exceptionHandling.UserAlreadyExistedException;
import com.ayaan.FinanceTracker.service.UsersService;
import com.ayaan.FinanceTracker.models.Users;

/**
 * Servlet implementation class SignupServlet
 */
//@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userDAO = new UserDAOImpl(); 
	UsersService userService = new UsersService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		try {
			userService.saveUsers(userName, email, password);
			response.setContentType("text/html");
			response.getWriter().append("User Added Successfully");
			response.sendRedirect("LoginServlet");
				
		}catch(SQLException e) {
			request.setAttribute("errorMessage", "Failed to proceed");
		    RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
		    rd.forward(request, response);
		}catch(Exception e) {
			request.setAttribute("errorMessage", "Unexpected Error Occured!");
		    RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
		    rd.forward(request, response);
		}
	}
}
