// 代码生成时间: 2025-08-04 22:05:04
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HibernateUnitTest {
    private SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        // Initialize the SessionFactory from hibernate.cfg.xml
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Test
    void testSave() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            // Assuming we have an entity class 'User'
            User user = new User();
            user.setName("John Doe");
            user.setEmail("john.doe@example.com");

            session.save(user);
            transaction.commit();

            // Assert that the entity was saved successfully
            assertTrue(user.getId() != null);
        } catch (Exception e) {
            fail("An error occurred while saving the entity: " + e.getMessage());
        }
    }

    @Test
    void testFetch() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            // Assuming we have a saved user with ID 1
            User user = session.get(User.class, 1L);

            assertNotNull(user, "User should be found in the database");
            assertEquals("John Doe", user.getName(), "Name does not match");

            transaction.commit();
        } catch (Exception e) {
            fail("An error occurred while fetching the entity: " + e.getMessage());
        }
    }

    @Test
    void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, 1L);
            user.setEmail("new.email@example.com");

            session.update(user);
            transaction.commit();

            User updatedUser = session.get(User.class, 1L);
            assertEquals("new.email@example.com", updatedUser.getEmail(), "Email should be updated");
        } catch (Exception e) {
            fail("An error occurred while updating the entity: " + e.getMessage());
        }
    }

    @Test
    void testDelete() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, 1L);

            session.delete(user);
            transaction.commit();

            assertNull(session.get(User.class, 1L), "User should be deleted from the database");
        } catch (Exception e) {
            fail("An error occurred while deleting the entity: " + e.getMessage());
        }
    }
}