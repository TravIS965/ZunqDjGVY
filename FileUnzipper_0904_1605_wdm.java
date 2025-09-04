// 代码生成时间: 2025-09-04 16:05:54
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A utility class for unzipping compressed files.
 * This class provides functionality to extract GZIP compressed TAR files.
 */
public class FileUnzipper {

    private static final String GZIP_EXTENSION = ".gz";
    private static final String TAR_EXTENSION = ".tar";

    /**
     * Unzips a GZIP compressed TAR file to the specified directory.
     * 
     * @param inputFile The GZIP compressed TAR file to be unzipped.
     * @param outputDirectory The directory where the files will be extracted.
     * @throws IOException If an I/O error occurs during unzipping.
     */
    public void unzipGzipTarFile(String inputFile, String outputDirectory) throws IOException {
        // Check if the input file is a GZIP compressed TAR file
        if (!inputFile.endsWith(GZIP_EXTENSION) || !inputFile.contains(TAR_EXTENSION)) {
            throw new IllegalArgumentException("Input file must be a GZIP compressed TAR file.");
        }

        try (InputStream fileInputStream = new FileInputStream(inputFile);
             GzipCompressorInputStream gzipInputStream = new GzipCompressorInputStream(fileInputStream);
             TarArchiveInputStream tarInputStream = new TarArchiveInputStream(gzipInputStream)) {

            // Iterate through the entries in the TAR file
            TarArchiveInputStream.TarEntry entry;
            while ((entry = (TarArchiveInputStream.TarEntry) tarInputStream.getNextEntry()) != null) {
                String fileName = entry.getName();
                File outputFile = new File(outputDirectory, fileName);

                // Create directories as needed
                if (entry.isDirectory()) {
                    outputFile.mkdirs();
                    continue;
                }

                // Extract file
                try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile))) {
                    IOUtils.copy(tarInputStream, outputStream);
                }
            }
        }
    }

    public static void main(String[] args) {
        FileUnzipper unzipper = new FileUnzipper();
        String inputFile = "/path/to/your/file.tar.gz"; // Replace with your actual file path
        String outputDirectory = "/path/to/your/output/directory"; // Replace with your actual output directory path

        try {
            unzipper.unzipGzipTarFile(inputFile, outputDirectory);
            System.out.println("File unzipped successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while unzipping the file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid file type: " + e.getMessage());
        }
    }
}
