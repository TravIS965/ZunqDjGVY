// 代码生成时间: 2025-10-20 03:56:03
package com.yourcompany.promotion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.ArrayList;

// Define Promotion class
class Promotion {
    private Long id;
    private String name;
    private String description;
    // getters and setters
}

// Define PromotionDao interface
interface PromotionDao {
    void addPromotion(Promotion promotion);
    Promotion getPromotion(Long id);
    List<Promotion> getAllPromotions();
}

// Define PromotionDaoImpl class
class PromotionDaoImpl implements PromotionDao {
    private SessionFactory sessionFactory;
    public PromotionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public void addPromotion(Promotion promotion) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(promotion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error in adding promotion", e);
        } finally {
            session.close();
        }
    }
    @Override
    public Promotion getPromotion(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Promotion.class, id);
        } finally {
            session.close();
        }
    }
    @Override
    public List<Promotion> getAllPromotions() {
        Session session = sessionFactory.openSession();
        List<Promotion> promotions;
        try {
            promotions = session.createQuery("FROM Promotion").list();
        } finally {
            session.close();
        }
        return promotions;
    }
}

// Define PromotionEngine class
public class PromotionEngine {
    private PromotionDao promotionDao;
    private SessionFactory sessionFactory;

    public PromotionEngine() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
        this.promotionDao = new PromotionDaoImpl(sessionFactory);
    }

    public void addPromotion(String name, String description) {
        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setDescription(description);
        promotionDao.addPromotion(promotion);
    }

    public Promotion getPromotion(Long id) {
        return promotionDao.getPromotion(id);
    }

    public List<Promotion> getAllPromotions() {
        return promotionDao.getAllPromotions();
    }
}
