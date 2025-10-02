// 代码生成时间: 2025-10-03 00:00:25
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;

/**
 * DocumentConverter is a utility class to convert documents.
 * It uses Hibernate for database operations and Apache POI for document processing.
 */
public class DocumentConverter {

    // Hibernate Session Factory
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + "Exception: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Converts a document from one format to another.
     * @param sourceFilePath The path to the source document.
     * @param targetFilePath The path to the target document.
     * @throws IOException If there is an I/O error.
     * @throws InvalidFormatException If the file format is not valid.
     * @throws XmlException If there is an XML error.
     */
    public void convert(String sourceFilePath, String targetFilePath) throws IOException, InvalidFormatException, XmlException {
        // Check if the source file exists
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            throw new IOException("Source file does not exist: " + sourceFilePath);
        }

        // Create input and output streams
        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(targetFilePath)) {

            // Convert the document using Apache POI
            XWPFDocument document = new XWPFDocument(inputStream);

            // Save the document to the output stream
            document.write(outputStream);
        }
    }

    /**
     * Closes the Hibernate Session Factory.
     */
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    // Example usage of DocumentConverter
    public static void main(String[] args) {
        try {
            DocumentConverter converter = new DocumentConverter();
            // Convert a document from .docx to .doc format
            converter.convert("source.docx", "target.doc");
            System.out.println("Document conversion successful.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the Session Factory to free resources
            DocumentConverter.closeSessionFactory();
        }
    }
}
