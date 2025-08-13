// 代码生成时间: 2025-08-13 19:30:27
import java.util.Random;

/**
 * RandomNumberGenerator provides functionality to generate random numbers.
 * This class adheres to Java best practices, includes error handling, 
 * and is designed for maintainability and extensibility.
 */
public class RandomNumberGenerator {

    private final Random random;

    /**
     * Constructs a new RandomNumberGenerator with a new Random instance.
     */
    public RandomNumberGenerator() {
        this.random = new Random();
    }

    /**
     * Generates a random integer within a specified range [min, max].
     * 
     * @param min the minimum value of the random number (inclusive)
     * @param max the maximum value of the random number (inclusive)
     * @return a random integer within the specified range
     * @throws IllegalArgumentException if min is greater than max
     */
    public int generateRandomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
        }
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Generates a random double within a specified range [min, max].
     * 
     * @param min the minimum value of the random number (inclusive)
     * @param max the maximum value of the random number (inclusive)
     * @return a random double within the specified range
     * @throws IllegalArgumentException if min is greater than max
     */
    public double generateRandomDouble(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
        }
        return min + (random.nextDouble() * (max - min));
    }

    /**
     * Main method to demonstrate the functionality of RandomNumberGenerator.
     * 
     * @param args command line arguments (not used in this program)
     */
    public static void main(String[] args) {
        // Instantiate RandomNumberGenerator
        RandomNumberGenerator rng = new RandomNumberGenerator();

        try {
            // Generate and print a random integer
            int randomInt = rng.generateRandomInt(1, 100);
            System.out.println("Random Integer: " + randomInt);

            // Generate and print a random double
            double randomDouble = rng.generateRandomDouble(1.0, 100.0);
            System.out.println("Random Double: " + randomDouble);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}