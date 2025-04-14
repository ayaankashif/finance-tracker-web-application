package com.ayaan.FinanceTracker.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users{
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer userId;
	 @Column(name = "user_name")
	 private String userName;
	 private String email;
	 private String password;
	 private Date registerDate;
	 
	 public Users() {
		 
	 }
	 
	 public Users(String userName, String email, String password, Date registerDate) {
		 this.userName = userName;
		 this.email = email;
		 this.password = password;
		 this.registerDate = registerDate;
	 }
	 
	 public Users(Integer userId, String userName, String email, String password, Date registerDate) {
		 this(userName, email, password, registerDate);
		 this.userId = userId;		 
	 }
	 

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}
	
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
}