// 代码生成时间: 2025-09-07 11:28:45
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FolderStructureOrganizer is a utility class that helps to organize a folder structure.
 * It provides methods to list files and folders in a directory, and to organize them based on
 * a specified pattern.
 */
public class FolderStructureOrganizer {

    private static final String DIRECTORY_SEPARATOR = "/";
    private static final String TEMP_FILE_EXTENSION = ".tmp";

    /**
     * Lists all files and directories in the given directory path.
     *
     * @param directoryPath The path of the directory.
     * @return A list of File objects representing the files and directories in the directory.
     */
    public List<File> listDirectoryContents(String directoryPath) {
        try {
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                throw new IllegalArgumentException("Provided path is not a valid directory.");
            }
            return new ArrayList<>(Arrays.asList(directory.listFiles()));
        } catch (Exception e) {
            // Log the exception or handle it according to the application's error handling policy
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Organizes the directory structure by moving files into subdirectories based on their extensions.
     *
     * @param directoryPath The path of the directory to organize.
     * @param extensionPattern The pattern in which to organize files by extensions.
     */
    public void organizeByExtension(String directoryPath, String extensionPattern) {
        try {
            List<File> files = listDirectoryContents(directoryPath);
            files.stream()
                .filter(File::isFile)
                .forEach(file -> {
                    String extension = getExtension(file.getName());
                    if (extension != null && extension.matches(extensionPattern)) {
                        try {
                            File targetDirectory = new File(directoryPath + DIRECTORY_SEPARATOR + extension);
                            if (!targetDirectory.exists()) {
                                targetDirectory.mkdirs();
                            }
                            File targetFile = new File(targetDirectory, file.getName());
                            if (!file.renameTo(targetFile)) {
                                Files.move(file.toPath(), targetFile.toPath());
                            }
                        } catch (IOException e) {
                            // Log the exception or handle it according to the application's error handling policy
                            e.printStackTrace();
                        }
                    }
                });
        } catch (Exception e) {
            // Log the exception or handle it according to the application's error handling policy
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the file extension from a given file name.
     *
     * @param fileName The name of the file.
     * @return The file extension or null if the file has no extension.
     */
    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return null;
    }

    /**
     * Main method for testing the FolderStructureOrganizer class.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        FolderStructureOrganizer organizer = new FolderStructureOrganizer();
        String directoryPath = "path/to/your/directory";
        String extensionPattern = "\.jpg|\.png";
        organizer.organizeByExtension(directoryPath, extensionPattern);
    }
}
