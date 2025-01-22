package com.ayaan.FinanceTracker.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ayaan.FinanceTracker.dao.ExpenseDAO;
import com.ayaan.FinanceTracker.models.Expense;
import com.ayaan.FinanceTracker.util.HibernateUtil;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class ExpenseDAOImpl implements ExpenseDAO {

    public void saveExpense(Expense expense) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(expense);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
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
            return session.createQuery("from Expense", Expense.class).list();
        }
    }

    public Double getExpenses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT e.expense FROM Expense e ";
            return session.createQuery(hql, Double.class).uniqueResult(); 
        }
    }

    public Double getExpenseBySourceFromExpense(String source) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT e.expense FROM Expense e " +
                         "JOIN e.incomeExpenseSourceId ies " +
                         "WHERE ies.incomeExpenseSource = :source";
            Query query = session.createQuery(hql);
            query.setParameter("source", source);

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

    public Double getExpensesBySourceId(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT e.expense FROM Expense e " + 
                        "JOIN e.incomeExpenseSourceId bt ";
            return session.createQuery(hql, Double.class).uniqueResult(); 
        }
    }
}
