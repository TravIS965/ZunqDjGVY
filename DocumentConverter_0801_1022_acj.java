// 代码生成时间: 2025-08-01 10:22:14
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.IOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentConverter {

    // Method to convert a document from Word to HTML format
    public String convertWordToHtml(String inputFilePath) {
        try {
            XWPFDocument document = new XWPFDocument(Files.newInputStream(Paths.get(inputFilePath)));
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            String htmlContent = paragraphs.stream()
                    .map(this::paragraphToHtml)
                    .collect(Collectors.joining("</p><p>"));
            return "<html><body>
" + htmlContent + "</p></body></html>";
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the document", e);
        }
    }

    // Helper method to convert a paragraph from Word to HTML
    private String paragraphToHtml(XWPFParagraph paragraph) {
        return "<p>" + paragraph.getText() + "</p>";
    }

    public static void main(String[] args) {
        DocumentConverter converter = new DocumentConverter();
        try {
            String htmlContent = converter.convertWordToHtml("path_to_your_document.docx");
            System.out.println(htmlContent);
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
