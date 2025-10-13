// 代码生成时间: 2025-10-13 22:37:43
package com.yourcompany.ems;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import com.yourcompany.ems.model.EnergyData;

public class EnergyManagementSystem {
    
    private static SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Method to get the current session
    public static Session getSession() throws Exception {
        return sessionFactory.openSession();
    }

    // Method to save energy data
    public static void saveEnergyData(EnergyData energyData) throws Exception {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(energyData);
            transaction.commit();
        }
    }

    // Method to update energy data
    public static void updateEnergyData(EnergyData energyData) throws Exception {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(energyData);
            transaction.commit();
        }
    }

    // Method to delete energy data
    public static void deleteEnergyData(EnergyData energyData) throws Exception {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(energyData);
            transaction.commit();
        }
    }

    // Method to find energy data by ID
    public static EnergyData findEnergyDataById(int id) throws Exception {
        try (Session session = getSession()) {
            return session.get(EnergyData.class, id);
        }
    }

    // Method to list all energy data
    public static List<EnergyData> findAllEnergyData() throws Exception {
        try (Session session = getSession()) {
            return session.createQuery("from EnergyData", EnergyData.class).list();
        }
    }
}
