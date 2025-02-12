package com.ayaan.FinanceTracker.daoImpl;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ayaan.FinanceTracker.dao.ExpenseDAO;
import com.ayaan.FinanceTracker.models.Expense;
import com.ayaan.FinanceTracker.util.HibernateUtil;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class ExpenseDAOImpl implements ExpenseDAO {

    public boolean saveExpense(Expense expense) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Serializable id = (Serializable) session.save(expense);
            transaction.commit();
            if(id != null) {
            	return true;
            }
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    public void updateExpense(Expense expense) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(expense);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteExpense(Expense expense) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // BankAccount bankAccount = session.get(BankAccount.class, id);
            if (expense != null) {
                session.delete(expense);
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

    public Expense getExpensebyId(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Expense.class, id);
        }
    }

    public List<Expense> getAllExpense() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            YearMonth currentMonth = YearMonth.now();
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            String hql = "FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate";
            return session.createQuery(hql, Expense.class)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .list();
        }
    }

    public Double getExpenseBySourceFromExpense(String source) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            YearMonth currentMonth = YearMonth.now();
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            String hql = "SELECT e.expense FROM Expense e " +
                    "JOIN e.incomeExpenseSourceId ies " +
                    "WHERE ies.incomeExpenseSource = :source AND e.date BETWEEN :startDate AND :endDate";
            Query query = session.createQuery(hql);
            query.setParameter("source", source);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);

            try {
                return (Double) query.getSingleResult();

            } catch (NoResultException e) {
                System.out.println("No income found for the given source.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
