// 代码生成时间: 2025-08-25 02:18:17
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Random;

public class PerformanceTest {

    // Holds the reference to the session factory
    private static SessionFactory sessionFactory = buildSessionFactory();

    // Method to build and return the session factory
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the session factory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + "Exception: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Method to close the session factory
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    // Method to perform performance test for creating entities
    public static void testCreatePerformance() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                // Assume Entity is a Hibernate entity class
                Entity entity = new Entity();
                entity.setName("Entity" + i);
                session.save(entity);
            }
            session.getTransaction().commit();
            long endTime = System.currentTimeMillis();
            System.out.println("Create operation took: " + (endTime - startTime) + " ms");
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to perform performance test for reading entities
    public static void testReadPerformance() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            long startTime = System.currentTimeMillis();
            Query query = session.createQuery("FROM Entity");
            List<Entity> entities = query.list();
            long endTime = System.currentTimeMillis();
            System.out.println("Read operation took: " + (endTime - startTime) + " ms");
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to perform performance test for updating entities
    public static void testUpdatePerformance() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            long startTime = System.currentTimeMillis();
            Random random = new Random();
            Query query = session.createQuery("FROM Entity");
            List<Entity> entities = query.list();
            for (Entity entity : entities) {
                entity.setName("Updated Entity" + random.nextInt());
                session.update(entity);
            }
            session.getTransaction().commit();
            long endTime = System.currentTimeMillis();
            System.out.println("Update operation took: " + (endTime - startTime) + " ms");
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to perform performance test for deleting entities
    public static void testDeletePerformance() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            long startTime = System.currentTimeMillis();
            Query query = session.createQuery("FROM Entity");
            List<Entity> entities = query.list();
            for (Entity entity : entities) {
                session.delete(entity);
            }
            session.getTransaction().commit();
            long endTime = System.currentTimeMillis();
            System.out.println("Delete operation took: " + (endTime - startTime) + " ms");
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Main method to run the performance tests
    public static void main(String[] args) {
        try {
            testCreatePerformance();
            testReadPerformance();
            testUpdatePerformance();
            testDeletePerformance();
        } finally {
            shutdown();
        }
    }
}
