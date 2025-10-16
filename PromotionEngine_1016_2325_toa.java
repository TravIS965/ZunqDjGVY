// 代码生成时间: 2025-10-16 23:25:51
package com.example.promotion;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;

/**
 * PromotionEngine serves as a central component to manage promotional activities.
 * It uses Hibernate to interact with the database.
 */
public class PromotionEngine {

    // Factory for creating Hibernate sessions
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private Session session;
    private Transaction transaction;

    public PromotionEngine() {
        session = sessionFactory.openSession();
    }

    /**
     * Start a new transaction.
     */
    public void beginTransaction() {
        transaction = session.beginTransaction();
    }

    /**
     * Commit the current transaction.
     */
    public void commitTransaction() {
        if (transaction != null) {
            transaction.commit();
        }
    }

    /**
     * Rollback the current transaction in case of an error.
     */
    public void rollbackTransaction() {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    /**
     * Close the Hibernate session.
     */
    public void closeSession() {
        if (session != null) {
            session.close();
        }
    }

    /**
     * Add a new promotion to the database.
     * @param promotion The promotion to be added.
     */
    public void addPromotion(Promotion promotion) {
        try {
            beginTransaction();
            session.save(promotion);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    /**
     * Fetch all promotions from the database.
     * @return A list of all promotions.
     */
    public List<Promotion> getAllPromotions() {
        try {
            beginTransaction();
            Query<Promotion> query = session.createQuery("FROM Promotion", Promotion.class);
            return query.getResultList();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
            return null;
        } finally {
            closeSession();
        }
    }

    /**
     * Fetch a promotion by its ID.
     * @param id The ID of the promotion to fetch.
     * @return The promotion with the specified ID.
     */
    public Promotion getPromotionById(int id) {
        try {
            beginTransaction();
            return session.get(Promotion.class, id);
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
            return null;
        } finally {
            closeSession();
        }
    }

    /**
     * Update an existing promotion in the database.
     * @param promotion The updated promotion.
     */
    public void updatePromotion(Promotion promotion) {
        try {
            beginTransaction();
            session.update(promotion);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    /**
     * Delete a promotion from the database.
     * @param promotion The promotion to be deleted.
     */
    public void deletePromotion(Promotion promotion) {
        try {
            beginTransaction();
            session.delete(promotion);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }
}
