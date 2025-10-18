// 代码生成时间: 2025-10-18 15:21:18
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigFileManager is a utility class to manage Hibernate configuration.
 * It provides method to create SessionFactory from configuration file.
 */
public class ConfigFileManager {

    private static SessionFactory sessionFactory;

    /**
     * Private constructor to prevent instantiation.
     */
    private ConfigFileManager() {
    }

    /**
     * Loads properties from the specified file and initializes the Hibernate SessionFactory.
     * @param configFilename The filename of the Hibernate configuration file.
     * @return The initialized SessionFactory.
     * @throws IOException If the configuration file cannot be loaded.
     */
    public static SessionFactory initializeSessionFactory(String configFilename) throws IOException {
        if (sessionFactory == null || !sessionFactory.isOpen()) {
            Properties properties = new Properties();
            try (FileInputStream inputStream = new FileInputStream(configFilename)) {
                properties.load(inputStream);
                Configuration configuration = new Configuration().setProperties(properties);
                sessionFactory = configuration.buildSessionFactory();
            } catch (IOException e) {
                throw new IOException("Error loading configuration file: " + configFilename, e);
            }
        }
        return sessionFactory;
    }

    /**
     * Closes the SessionFactory.
     */
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    /**
     * Main method for testing the configuration file manager.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            SessionFactory factory = initializeSessionFactory("hibernate.cfg.xml");
            System.out.println("SessionFactory initialized successfully.");
            // Perform database operations using the factory
            // ...
            closeSessionFactory();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
