package com.ayaan.FinanceTracker.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ayaan.FinanceTracker.dao.IncomeDAO;
import com.ayaan.FinanceTracker.models.Income;
import com.ayaan.FinanceTracker.util.HibernateUtil;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class IncomeDAOImpl implements IncomeDAO {

    public void saveIncome(Income income) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(income);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateIncome(Income income) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(income);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteIncome(Income income) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // BankAccount bankAccount = session.get(BankAccount.class, id);
            if (income != null) {
                session.delete(income);
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

    public Income getIncomebyId(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Income.class, id);
        }
    }

    public List<Income> getAllIncome() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Income", Income.class).list();
        }
    }

    public Double getIncomes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT SUM(i.income) FROM Income i";
            return session.createQuery(hql, Double.class).uniqueResult(); 
        }
    }

    public Double getIncomeBySourceFromIncomes(String source) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT i.income FROM Income i " +
                         "JOIN i.incomeSources ies " +
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
    
}
