package com.ayaan.FinanceTracker.dao;

import java.util.List;

import com.ayaan.FinanceTracker.models.AccountTransaction;

public interface AccountTransactionDAO {

    void saveTransaction(AccountTransaction accountTransaction);

    void updateTransaction(AccountTransaction accountTransaction);
    
    void deleteTransactions(AccountTransaction accountTransaction);
    
    AccountTransaction getTransactionById(Integer id);
    
    List<Object[]> getBankTransaction();
    
    List<Object[]> accountDashboard();
    
    List<Object[]> monthlyStats(String type);
    
    List<AccountTransaction> getAllTransactions();
    
    Double getTotalBankBalance();
}
