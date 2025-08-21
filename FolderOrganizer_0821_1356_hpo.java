// 代码生成时间: 2025-08-21 13:56:42
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FolderOrganizer is a utility class to help organize a folder by moving files into
 * subfolders based on certain criteria.
 */
public class FolderOrganizer {

    /**
     * Organizes the files in the given directory into subfolders.
     *
     * @param sourceDir The directory to organize.
     * @param subfolderStrategy A strategy to determine the subfolder for each file.
     */
    public void organizeFiles(File sourceDir, SubfolderStrategy subfolderStrategy) {
        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            throw new IllegalArgumentException("The source directory does not exist or is not a directory.");
        }

        try (Stream<Path> paths = Files.walk(Paths.get(sourceDir.getAbsolutePath()))) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> {
                        File subfolder = subfolderStrategy.determineSubfolder(new File(file.toUri()));
                        File destination = new File(subfolder, file.getName());
                        moveFile(file, destination);
                    });
        } catch (IOException e) {
            throw new RuntimeException("Error organizing files.", e);
        }
    }

    /**
     * Moves a file from its current location to a new destination.
     *
     * @param source The source file to move.
     * @param destination The destination file.
     */
    private void moveFile(File source, File destination) {
        try {
            Files.move(source.toPath(), destination.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error moving file from " + source.getAbsolutePath() + " to " + destination.getAbsolutePath(), e);
        }
    }

    /**
     * A strategy interface to determine the subfolder for a given file.
     */
    public interface SubfolderStrategy {
        File determineSubfolder(File file);
    }

    /**
     * A simple implementation of SubfolderStrategy that organizes files by their extension.
     */
    public static class ByExtensionStrategy implements SubfolderStrategy {

        @Override
        public File determineSubfolder(File file) {
            String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
            File subfolder = new File(file.getParentFile(), extension);
            if (!subfolder.exists()) {
                subfolder.mkdirs();
            }
            return subfolder;
        }
    }

    public static void main(String[] args) {
        try {
            File sourceDir = new File("path/to/source/directory");
            FolderOrganizer organizer = new FolderOrganizer();
            organizer.organizeFiles(sourceDir, new ByExtensionStrategy());
            System.out.println("Files have been organized.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
