package com.ayaan.FinanceTracker.exceptionHandling;

public class InvalidIDException extends ExpenseManager{
    public InvalidIDException(String message){
        super(message);
    }
}
