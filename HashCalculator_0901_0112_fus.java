// 代码生成时间: 2025-09-01 01:12:08
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Base64;

public class HashCalculator {

    // Main method for testing purposes
    public static void main(String[] args) {
        try {
            String inputString = "Hello, World!";
            String hashValue = calculateHash(inputString);
            System.out.println("Input String: " + inputString);
            System.out.println("Hash Value: " + hashValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates hash value for a given input string.
     * 
     * @param input The input string to calculate hash for.
     * @return A string representing the hash value.
     * @throws Exception If any error occurs during hash calculation.
     */
    public static String calculateHash(String input) throws Exception {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty.");
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
