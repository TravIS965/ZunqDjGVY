// 代码生成时间: 2025-09-07 00:47:37
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FolderStructureOrganizer is a utility class designed to organize files within a directory.
 * It categorizes files by their extensions into subdirectories.
 */
public class FolderStructureOrganizer {

    private static final String EXTENSIONS_DIR = "extensions";

    /**
     * Organizes files in the specified directory by their extensions into subdirectories.
     *
     * @param sourceDirectory The directory to organize.
     * @throws IOException If an I/O error occurs.
     */
    public void organize(String sourceDirectory) throws IOException {
        File directory = new File(sourceDirectory);

        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The provided directory does not exist or is not a directory.");
        }

        File extensionsDir = new File(directory, EXTENSIONS_DIR);
        if (!extensionsDir.exists() && !extensionsDir.mkdir()) {
            throw new IOException("Unable to create extensions directory.");
        }

        List<File> files = listFiles(directory);
        for (File file : files) {
            String extension = getFileExtension(file.getName());
            if (extension != null) {
                File targetDir = new File(extensionsDir, extension);
                if (!targetDir.exists() && !targetDir.mkdir()) {
                    throw new IOException("Unable to create directory for extension: " + extension);
                }
                Files.move(file.toPath(), targetDir.toPath().resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    /**
     * Retrieves the file extension from the given filename.
     *
     * @param filename The name of the file.
     * @return The file extension or null if the file has no extension.
     */
    private String getFileExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1 || index == filename.length() - 1) {
            return null;
        }
        return filename.substring(index + 1);
    }

    /**
     * Lists all files in the specified directory.
     *
     * @param directory The directory to list files from.
     * @return A list of files in the directory.
     */
    private List<File> listFiles(File directory) {
        return new ArrayList<>(File.class.cast(directory.listFiles()));
    }

    public static void main(String[] args) {
        try {
            FolderStructureOrganizer organizer = new FolderStructureOrganizer();
            organizer.organize("/path/to/your/directory");
            System.out.println("Files have been organized.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}