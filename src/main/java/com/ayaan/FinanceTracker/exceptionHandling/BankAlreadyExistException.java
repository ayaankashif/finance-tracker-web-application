package com.ayaan.FinanceTracker.exceptionHandling;

public class BankAlreadyExistException extends ExpenseManager {
    public BankAlreadyExistException(String message){
        super(message);
    }
}
