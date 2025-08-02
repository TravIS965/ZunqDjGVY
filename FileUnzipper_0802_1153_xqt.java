// 代码生成时间: 2025-08-02 11:53:40
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A utility class for unzipping files using Java.
 */
public class FileUnzipper {

    /**
     * Unzips a zip file to a specified directory.
     *
     * @param zipFilePath Path to the zip file.
     * @param destDirectory Path to the destination directory.
     * @throws IOException If an I/O error occurs during unzipping.
     */
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // Iterate through the entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // If the entry is a file, extract it
                extractFile(zipIn, filePath);
            } else {
                // If the entry is a directory, create it
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a file from the zip input stream to the specified path.
     *
     * @param zipIn The zip input stream.
     * @param filePath Path where the file will be extracted.
     * @throws IOException If an I/O error occurs during file extraction.
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    /**
     * Main method for testing the unzip functionality.
     *
     * @param args Command line arguments (not used in this example).
     */
    public static void main(String[] args) {
        try {
            String zipFilePath = "path/to/your.zip";
            String destDirectory = "path/to/destination";
            unzip(zipFilePath, destDirectory);
            System.out.println("Unzipping complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}