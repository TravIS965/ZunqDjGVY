// 代码生成时间: 2025-10-26 14:11:05
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class AchievementSystem {
    // Hibernate Session Factory
    private static SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + " Root cause: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Get a Session from the Session Factory
     *
     * @return Session
     */
    public static Session getSession() throws Exception {
        return sessionFactory.openSession();
    }
    
    /**
     * Close the Session Factory
     */
    public static void shutdown() {
        getSessionFactory().close();
    }
    
    /**
     * Get the Session Factory
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    // Dummy Achievement entity class for demonstration purposes
    public static class Achievement {
        private int id;
        private String title;
        private String description;
        
        // Constructors, getters and setters are omitted for brevity
    }
    
    /**
     * Add a new achievement to the system
     *
     * @param achievement The achievement to add
     * @return int The ID of the newly added achievement
     */
    public int addAchievement(Achievement achievement) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(achievement);
            transaction.commit();
            return achievement.getId();
        } catch (RuntimeException e) {
            if (transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    /**
     * Retrieve an achievement by ID
     *
     * @param id The ID of the achievement to retrieve
     * @return Achievement The retrieved achievement or null if not found
     */
    public Achievement getAchievement(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return (Achievement) session.get(Achievement.class, id);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    /**
     * Update an existing achievement
     *
     * @param achievement The achievement to update
     */
    public void updateAchievement(Achievement achievement) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(achievement);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    /**
     * Delete an achievement by ID
     *
     * @param id The ID of the achievement to delete
     */
    public void deleteAchievement(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Achievement achievement = (Achievement) session.get(Achievement.class, id);
            if (achievement != null) {
                session.delete(achievement);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    /**
     * List all achievements
     *
     * @return List<Achievement> A list of all achievements
     */
    public List<Achievement> listAllAchievements() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("from Achievement").list();
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }
}
