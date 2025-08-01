// 代码生成时间: 2025-08-02 04:34:09
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BatchFileRenamer {

    /*
     * 重命名文件夹内所有文件
     *
     * @param directoryPath 文件夹路径
     * @param newBaseName 新的基础文件名
     * @return 重命名成功的文件数量
     */
    public int renameFilesInDirectory(String directoryPath, String newBaseName) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        int renamedFilesCount = 0;

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    try {
                        Path newPath = Paths.get(directoryPath, newBaseName + fileName.substring(fileName.lastIndexOf(".")));
                        Files.move(file.toPath(), newPath);
                        renamedFilesCount++;
                    } catch (Exception e) {
                        System.err.println("Error renaming file: " + fileName + ". Error: " + e.getMessage());
                    }
                }
            }
        }

        return renamedFilesCount;
    }

    /*
     * 主方法，程序入口
     */
    public static void main(String[] args) {
        BatchFileRenamer renamer = new BatchFileRenamer();
        String directoryPath = "/path/to/directory";
        String newBaseName = "newName";

        try {
            int renamedFiles = renamer.renameFilesInDirectory(directoryPath, newBaseName);
            System.out.println("Renamed " + renamedFiles + " files successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
