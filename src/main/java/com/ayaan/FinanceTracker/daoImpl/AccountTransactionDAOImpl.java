package com.ayaan.FinanceTracker.daoImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ayaan.FinanceTracker.dao.AccountTransactionDAO;
import com.ayaan.FinanceTracker.models.AccountTransaction;
import com.ayaan.FinanceTracker.util.HibernateUtil;

public class AccountTransactionDAOImpl implements AccountTransactionDAO {

    public void saveTransaction(AccountTransaction accountTransaction) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(accountTransaction);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateTransaction(AccountTransaction accountTransaction) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(accountTransaction);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteTransactions(AccountTransaction accountTransaction) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // BankAccount bankAccount = session.get(BankAccount.class, id);
            if (accountTransaction != null) {
                session.delete(accountTransaction);
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

    public AccountTransaction getTransactionById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(AccountTransaction.class, id);
        }
    }

    public List<Object[]> getBankTransaction() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            YearMonth currentMonth = YearMonth.now();
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            String hql = "SELECT ba.name, at.transactionAmt, at.transactionType FROM AccountTransaction at " +
                    "JOIN at.bankAccId ba " +
                    "WHERE at.transactionDate BETWEEN :startDate AND :endDate";
            return session.createQuery(hql, Object[].class)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .list();
        }
    }

    public List<Object[]> accountDashboard() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            YearMonth currentMonth = YearMonth.now();
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            String hql = "SELECT ba.bankAccId, ba.name, sum(at.transactionAmt) FROM AccountTransaction at " +
                    "JOIN at.bankAccId ba WHERE at.transactionDate BETWEEN :startDate AND :endDate " +
                    "GROUP BY at.bankAccId";
            return session.createQuery(hql, Object[].class)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .list();
        }
    }

    public List<Object[]> monthlyStats(String type) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            YearMonth currentMonth = YearMonth.now();
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            String hql = "SELECT ba.name, at.transactionAmt FROM AccountTransaction at " +
                    "JOIN at.bankAccId ba " +
                    "WHERE at.transactionType = :type AND at.transactionDate BETWEEN :startDate AND :endDate";
            return session.createQuery(hql, Object[].class)
                    .setParameter("type", type)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .list();
        }
    }

    public List<AccountTransaction> getAllTransactions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            YearMonth currentMonth = YearMonth.now();
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            String hql = "SELECT * from AccountTransaction WHERE at.transactionDate BETWEEN :startDate AND :endDate";
            return session.createQuery(hql, AccountTransaction.class)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .list();
        }
    }
}
