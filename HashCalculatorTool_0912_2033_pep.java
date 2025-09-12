// 代码生成时间: 2025-09-12 20:33:49
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * A utility class to calculate hash values using Java's built-in security libraries.
 * Supports various hash algorithms such as SHA-256, MD5, etc.
 */
public class HashCalculatorTool {

    /**
     * Calculates the hash of the given input string using the specified algorithm.
     *
     * @param input The input string to hash.
     * @param algorithm The name of the hash algorithm to use (e.g., "SHA-256", "MD5").
     * @return The hash of the input string as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the specified algorithm is not available.
     * @throws IllegalArgumentException If the input is null.
     */
    public static String calculateHash(String input, String algorithm) throws NoSuchAlgorithmException {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        // Get an instance of the MessageDigest with the specified algorithm.
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // Update the digest using the bytes of the input string.
        digest.update(input.getBytes(StandardCharsets.UTF_8));

        // Calculate the hash and convert it to a hexadecimal string.
        byte[] hashBytes = digest.digest();
        StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * Main method for testing the hash calculator utility.
     * @param args Command line arguments (not used in this example).
     */
    public static void main(String[] args) {
        try {
            String input = "Hello, World!";
            String algorithm = "SHA-256";

            String hash = calculateHash(input, algorithm);
            System.out.println("Input: " + input);
            System.out.println("Hash (