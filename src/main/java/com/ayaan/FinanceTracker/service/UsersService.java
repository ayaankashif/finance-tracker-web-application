package com.ayaan.FinanceTracker.service;

import java.sql.Date;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaan.FinanceTracker.daoImpl.UserDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.UserAlreadyExistedException;
import com.ayaan.FinanceTracker.dao.UserDAO;
import com.ayaan.FinanceTracker.models.Users;

public class UsersService{
//	private static final Logger logger = LoggerFactory.getLogger(UsersService.class);
	UserDAO userDAO = new UserDAOImpl();
	
	public boolean saveUsers(String userName, String email, String password) throws SQLException{
		
		Users user = new Users(userName, email, password,  new Date(System.currentTimeMillis()));
		userDAO.saveUser(user);
		
		return false;
	}
	
}