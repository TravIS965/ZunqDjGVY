// 代码生成时间: 2025-08-14 19:39:40
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class TestDataGenerator {

    private static final Logger logger = LoggerFactory.getLogger(TestDataGenerator.class);
    private static final Random random = new Random();
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + "
" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void generateTestData() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Generate and save 100 test entities
            for (int i = 0; i < 100; i++) {
                // Assuming an entity called 'TestEntity' with a 'name' field
                TestEntity entity = new TestEntity();
                entity.setName("Test Name " + i);
                session.save(entity);
            }

            transaction.commit();
        } catch (Exception e) {
            logger.error("Error generating test data", e);
        }
    }

    public static void shutdown() {
        // Close the SessionFactory when you're done
        sessionFactory.close();
    }

    private static class TestEntity {
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        try {
            generateTestData();
        } finally {
            shutdown();
        }
    }
}