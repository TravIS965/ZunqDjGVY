// 代码生成时间: 2025-08-29 01:04:55
package com.example.configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Properties;

@Entity
public class ConfigurationManager {

    private static SessionFactory sessionFactory;

    // Static block to initialize the SessionFactory
    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, when logging is available
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Inner class to handle database operations
    public static class DatabaseOperations {
        public static void saveOrUpdateConfigSetting(String key, String value) {
            Session session = null;
            Transaction tx = null;
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();

                // Assuming a ConfigSetting entity with key and value fields
                ConfigSetting configSetting = new ConfigSetting();
                configSetting.setKey(key);
                configSetting.setValue(value);
                session.saveOrUpdate(configSetting);

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }

        public static String getConfigSetting(String key) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
                ConfigSetting configSetting = session.get(ConfigSetting.class, key);
                if (configSetting != null) {
                    return configSetting.getValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null) {
                    session.close();
                }
            }
            return null;
        }
    }
}

/*
 * ConfigSetting.java
 *
 * This is a simple entity class representing a configuration setting.
 */
package com.example.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ConfigSetting {

    @Id
    @Column(name = "config_key")
    private String key;

    @Column(name = "config_value")
    private String value;

    // Getters and setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
