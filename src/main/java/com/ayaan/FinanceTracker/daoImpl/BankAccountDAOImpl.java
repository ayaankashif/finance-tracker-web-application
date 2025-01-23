package com.ayaan.FinanceTracker.daoImpl;

import com.ayaan.FinanceTracker.dao.BankAccountDAO;
import com.ayaan.FinanceTracker.models.BankAccount;
import com.ayaan.FinanceTracker.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class BankAccountDAOImpl implements BankAccountDAO {

    public void saveBankAccount(BankAccount bankAccount) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(bankAccount);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateBankAccount(BankAccount bankAccount) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(bankAccount);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // BankAccount bankAccount = session.get(BankAccount.class, id);
            if (bankAccount != null) {
                session.delete(bankAccount);
                System.out.println("Bank Account is deleted");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public BankAccount getBankAccountByCondition(String bankName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM BankAccount WHERE name = :bankName";
            return session.createQuery(hql, BankAccount.class)
                    .setParameter("bankName", bankName)
                    .uniqueResult();
        }
    }

    public BankAccount getBankAccountById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(BankAccount.class, id);
        }
    }

    public List<BankAccount> getAllBankAccounts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from BankAccount", BankAccount.class).list();
        }
    }
}
