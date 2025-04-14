package com.ayaan.FinanceTracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayaan.FinanceTracker.daoImpl.UserDAOImpl;
import com.ayaan.FinanceTracker.dao.UserDAO;
import com.ayaan.FinanceTracker.exceptionHandling.InvalidCredentialsException;
import com.ayaan.FinanceTracker.exceptionHandling.UserAlreadyExistedException;
import com.ayaan.FinanceTracker.models.Users;
import com.ayaan.FinanceTracker.service.UsersService;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsersService userService = new UsersService();
	UserDAO userDAO = new UserDAOImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("login.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("userName");
		String pwd = request.getParameter("password");

		try {
		    response.setContentType("text/html");

		    Users userLogin = userDAO.userLogin(name, pwd);
		    if (userLogin == null) {
		        throw new InvalidCredentialsException("Invalid Username or Password");
		    }

		    Cookie loginCookie = new Cookie("name", name);
		    loginCookie.setMaxAge(30 * 60);
		    response.addCookie(loginCookie);

		    response.sendRedirect("DashboardServlet");

		} catch (InvalidCredentialsException e) {
		    request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		    rd.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", "Unexpected error occurred!");
		    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		    rd.forward(request, response);
		}
	}
}
