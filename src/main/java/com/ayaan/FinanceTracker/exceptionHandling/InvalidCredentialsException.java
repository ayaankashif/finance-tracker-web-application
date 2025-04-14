package com.ayaan.FinanceTracker.exceptionHandling;

public class InvalidCredentialsException extends ExpenseManager{
    public InvalidCredentialsException(String message){
        super(message);
    }
}
