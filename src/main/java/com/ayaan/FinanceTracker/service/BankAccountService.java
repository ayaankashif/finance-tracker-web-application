package com.ayaan.FinanceTracker.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayaan.FinanceTracker.daoImpl.BankAccountDAOImpl;
import com.ayaan.FinanceTracker.exceptionHandling.BankAlreadyExistException;
import com.ayaan.FinanceTracker.exceptionHandling.DataAccessException;
import com.ayaan.FinanceTracker.exceptionHandling.InvalidIDException;
import com.ayaan.FinanceTracker.exceptionHandling.invalidInputException;
import com.ayaan.FinanceTracker.models.BankAccount;

public class BankAccountService {
    private Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    BankAccountDAOImpl bankAccountDAO = new BankAccountDAOImpl();

    public boolean addBankAccount(String bankName) throws BankAlreadyExistException, SQLException {
    	
            BankAccount bankAccount = bankAccountDAO.getBankAccountByCondition(bankName);
            if (bankAccount != null) {
                throw new BankAlreadyExistException("Bank Account already exists");
            }
            bankAccount = new BankAccount(bankName, new Date(System.currentTimeMillis()));
            bankAccountDAO.saveBankAccount(bankAccount);
            logger.info("Bank Account Added");
        
            return false;
    }

    public void updateBankAccount() {
        try {
            System.out.println("Bank Name: ");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            System.out.println("Bank Account ID: ");
            Integer id = scanner.nextInt();
            
            BankAccount bankAccount = new BankAccount(id, name, new Date(System.currentTimeMillis()));
            bankAccountDAO.updateBankAccount(bankAccount);
            logger.info("Bank Account Updated");

        } catch (InputMismatchException e) {
            logger.error("Invalid input: {}", e.getMessage());
        } catch(Exception e){
            logger.error("An unexpented error occured: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void listBankAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<BankAccount> bank = bankAccountDAO.getAllBankAccounts();
            if (bank == null) {
                throw new DataAccessException("No bank Account Found!");
            }
            
            request.setAttribute("bankName", bank);
            
			/*
			 * System.out.println("Bank Account List: ");
			 * System.out.printf("%-15s %-14s %-15s%n", "Bank ID", "Bank Name",
			 * "Account Date");
			 * System.out.println("----------------------------------------------");
			 * bank.forEach(bankAccount -> System.out.printf("%-15s %-14s %-15s%n",
			 * bankAccount.getBankAccId(), bankAccount.getName(),
			 * bankAccount.getAccountDate()));
			 */

        } catch (DataAccessException e) {
            logger.error("Database error while fetching income sources: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occured: {}", e.getMessage());
            e.printStackTrace();
        }
    }

}