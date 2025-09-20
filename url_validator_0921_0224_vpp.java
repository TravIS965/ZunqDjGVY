// 代码生成时间: 2025-09-21 02:24:45
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.util.Scanner;

/**
 * A utility class for validating the validity of a given URL.
 */
public class URLValidator {

    /**
     * Validates the URL and returns the HTTP response code.
     * 
     * @param urlString The URL to validate.
     * @return The HTTP response code if successful, otherwise an error message.
     */
    public static String validateURL(String urlString) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000); // 5 seconds timeout
            connection.setReadTimeout(5000); // 5 seconds timeout

            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                return "Valid URL, HTTP Response Code: " + responseCode;
            } else {
                return "Invalid URL, HTTP Response Code: " + responseCode;
            }
        } catch (IOException e) {
            return "Invalid URL, Error: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Main method to test the URL validation functionality.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter URL to validate: ");
        String urlString = scanner.nextLine();
        scanner.close();

        String result = validateURL(urlString);
        System.out.println(result);
    }
}
