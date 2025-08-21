// 代码生成时间: 2025-08-22 03:53:41
 * @author [Your Name]
 * @version 1.0
 * @since [Date]
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LogParser {

    /*
     * Parses a log file and prints the extracted information.
     * 
     * @param logFilePath The path to the log file to be parsed.
     */
    public static void parseLogFile(String logFilePath) {
        try {
            File logFile = new File(logFilePath);
            Scanner scanner = new Scanner(logFile);
            Pattern logEntryPattern = Pattern.compile("(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}),(.*)");

            // Read the log file line by line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = logEntryPattern.matcher(line);

                // Check if the current line matches the log entry pattern
                if (matcher.find()) {
                    String timestamp = matcher.group(1);
                    String message = matcher.group(2);
                    System.out.println("Timestamp: " + timestamp + ", Message: " + message);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Log file not found.");
            e.printStackTrace();
        }
    }

    /*
     * Main method to run the log parser tool.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java LogParser <log_file_path>");
            return;
        }

        String logFilePath = args[0];
        parseLogFile(logFilePath);
    }
}