package com.ayaan.FinanceTracker.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ayaan.FinanceTracker.dao.IncomeExpenseSourcesDAO;
import com.ayaan.FinanceTracker.models.IncomeExpenseSources;
import com.ayaan.FinanceTracker.util.HibernateUtil;
import jakarta.persistence.Query;

public class IncomeExpenseSourcesDAOImpl implements IncomeExpenseSourcesDAO {

    public boolean saveIncomeExpenseSource(IncomeExpenseSources incomeExpenseSources) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Serializable id = (Serializable) session.save(incomeExpenseSources);
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

    public boolean updateIncomeExpenseSources(IncomeExpenseSources incomeExpenseSources) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Serializable id = (Serializable) session.save(incomeExpenseSources);
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

    public void updateMonthlyBudget(String sourceName, Double newBudget) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "UPDATE IncomeExpenseSources SET monthlyBudget = :newBudget WHERE incomeExpenseSource = :sourceName";
            Query query = session.createQuery(hql);
            query.setParameter("newBudget", newBudget);
            query.setParameter("sourceName", sourceName);

            int rowsAffected = query.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteIncome(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            IncomeExpenseSources incomeExpenseSources = session.get(IncomeExpenseSources.class, id);
            if (incomeExpenseSources != null) {
                session.delete(incomeExpenseSources);
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

    public IncomeExpenseSources getIncomeExpenseSourceByCondition(String source) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM IncomeExpenseSources WHERE incomeExpenseSource = :source";
            return session.createQuery(hql, IncomeExpenseSources.class)
                    .setParameter("source", source)
                    .uniqueResult();
        }
    }

    public IncomeExpenseSources getIncomeExpenseSourcesbyId(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(IncomeExpenseSources.class, id);
        }
    }

    // public List<IncomeExpenseSources> getAllIncomeExpenseSources(char type) {
    //     try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    //         String hql = "FROM IncomeExpenseSources WHERE type = :type";
    //         return session.createQuery(hql, IncomeExpenseSources.class)
    //                 .setParameter("type", type)
    //                 .list();
    //     }
    // }
    
    public List<Object[]> displayExpenseSrcWithBudget() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	String hql = "SELECT ies.incomeExpenseSource, bt.name FROM IncomeExpenseSources ies INNER JOIN ies.budgetTracker bt";
        			return session.createQuery(hql, Object[].class)
                    .getResultList();
        }
    }
    
    public List<IncomeExpenseSources> getAllIncomeExpenseSource() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from IncomeExpenseSources", IncomeExpenseSources.class).list();
        }
    }

    @Override
    public List<IncomeExpenseSources> getAllIncomeExpenseSources(char type) {
        throw new UnsupportedOperationException("Unimplemented method 'getAllIncomeExpenseSources'");
    }   
}