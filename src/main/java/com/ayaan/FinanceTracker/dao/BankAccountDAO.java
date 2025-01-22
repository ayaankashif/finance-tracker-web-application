package com.ayaan.FinanceTracker.dao;

import java.util.List;

import com.ayaan.FinanceTracker.models.BankAccount;

public interface BankAccountDAO {

    void saveBankAccount(BankAccount bankAccount);

    void updateBankAccount(BankAccount bankAccount);

    void deleteBankAccount(BankAccount bankAccount);

    BankAccount getBankAccountByCondition(String bankName);

    BankAccount getBankAccountById(Integer id);

    List<BankAccount> getAllBankAccounts();
}
