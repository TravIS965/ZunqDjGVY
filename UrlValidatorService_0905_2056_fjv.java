// 代码生成时间: 2025-09-05 20:56:58
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.validator.routines.UrlValidator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class UrlValidatorService {

    // Hibernate configuration
    private static final String HIBERNATE_CFG_XML = "hibernate.cfg.xml";
    private static Session session;
    private static UrlValidator urlValidator;

    static {
        try {
            // Set up Hibernate configuration
            Configuration configuration = new Configuration().configure(HIBERNATE_CFG_XML);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            session = configuration.buildSessionFactory(serviceRegistry).openSession();

            // Initialize URL validator
            urlValidator = new UrlValidator();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Method to validate URL
    public static boolean validateUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            // Handle exceptions for invalid URLs
            System.out.println("Error validating URL: " + e.getMessage());
            return false;
        }
    }

    // Method to check if the URL is valid using Apache Commons Validator
    public static boolean isUrlValid(String urlString) {
        return urlValidator.isValid(urlString);
    }

    public static void main(String[] args) {
        // Test URL validation
        String validUrl = "https://www.example.com";
        String invalidUrl = "https://invalid-url.com";

        System.out.println("Valid URL: " + validUrl + " - " + isUrlValid(validUrl));
        System.out.println("Invalid URL: " + invalidUrl + " - " + isUrlValid(invalidUrl));
    }
}
