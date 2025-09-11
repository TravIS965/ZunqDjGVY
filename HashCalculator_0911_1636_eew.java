// 代码生成时间: 2025-09-11 16:36:50
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A utility class to calculate hash values for a given string input using various algorithms.
 */
public class HashCalculator {

    /**
     * Calculates the hash value of the provided input string using the specified algorithm.
     *
     * @param input The string to hash.
     * @param algorithm The hashing algorithm to use (e.g., "SHA-256", "MD5").
     * @return The hexadecimal representation of the hash value.
     * @throws NoSuchAlgorithmException If the specified algorithm is not available.
     */
    public static String calculateHash(String input, String algorithm) throws NoSuchAlgorithmException {
        // Get an instance of the MessageDigest class for the specified algorithm
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // Update the digest using the input bytes.
        digest.update(input.getBytes(StandardCharsets.UTF_8));

        // Perform the final calculation of the hash
        byte[] hashBytes = digest.digest();

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    /**
     * Main method for demonstration purposes.
     *
     * @param args Command line arguments (not used in this example).
     */
    public static void main(String[] args) {
        try {
            String input = "Hello, World!";
            String hashSHA256 = calculateHash(input, "SHA-256");
            String hashMD5 = calculateHash(input, "MD5");

            System.out.println("SHA-256 Hash: " + hashSHA256);
            System.out.println("MD5 Hash: " + hashMD5);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Hash algorithm not found: " + e.getMessage());
        }
    }
}