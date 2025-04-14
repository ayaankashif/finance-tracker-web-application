package com.ayaan.FinanceTracker.dao;

import java.util.List;

import com.ayaan.FinanceTracker.models.Users;

public interface UserDAO {

    boolean saveUser(Users user);

    void updateUser(Users user);

    void deleteUser(Users user);

    Users userLogin(String UserName, String password);

    Users getUserById(Integer id);

    List<Users> getAllUsers();
}
