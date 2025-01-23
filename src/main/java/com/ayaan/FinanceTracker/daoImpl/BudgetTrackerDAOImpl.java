package com.ayaan.FinanceTracker.daoImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ayaan.FinanceTracker.dao.BudgetTrackerDAO;
import com.ayaan.FinanceTracker.models.BudgetTracker;
import com.ayaan.FinanceTracker.util.HibernateUtil;

public class BudgetTrackerDAOImpl implements BudgetTrackerDAO {

    public void saveBudget(BudgetTracker budgetTracker) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(budgetTracker);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateBudget(BudgetTracker budgetTracker) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(budgetTracker);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteBudget(BudgetTracker budgetTracker) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // BankAccount bankAccount = session.get(BankAccount.class, id);
            if (budgetTracker != null) {
                session.delete(budgetTracker);
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

    public BudgetTracker getBudgetById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(BudgetTracker.class, id);
        }
    }

    public List<Object[]> displayIncome() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            YearMonth currentMonth = YearMonth.now();
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            String hql = "SELECT i.name, i.income, ies.monthlyBudget FROM Income i " +
                    "JOIN i.incomeSources ies WHERE i.date BETWEEN :startDate AND :endDate";
            return session.createQuery(hql, Object[].class)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .getResultList();
        }
    }

    public List<Object[]> displayExpense() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            YearMonth currentMonth = YearMonth.now();
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            String hql = "SELECT e.name, e.expense, ies.monthlyBudget, bt.name FROM Expense e " +
                    "JOIN e.incomeExpenseSourceId ies LEFT JOIN ies.budgetTracker bt WHERE e.date BETWEEN :startDate AND :endDate";
            return session.createQuery(hql, Object[].class)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .getResultList();
        }
    }

    public BudgetTracker getBudgetByCondition(String source) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM BudgetTracker WHERE name = :source";
            return session.createQuery(hql, BudgetTracker.class)
                    .setParameter("source", source)
                    .uniqueResult();
        }
    }

    public List<BudgetTracker> getAllBudgets() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM BudgetTracker", BudgetTracker.class).list();
        }
    }
}
