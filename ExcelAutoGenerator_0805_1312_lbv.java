// 代码生成时间: 2025-08-05 13:12:54
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExcelAutoGenerator {

    /**
     * 生成Excel表格文件
     *
     * @param data 包含要写入Excel的数据的二维数组
     * @param fileName 要生成的Excel文件名
     */
    public static void generateExcel(Object[][] data, String fileName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(