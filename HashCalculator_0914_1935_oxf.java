// 代码生成时间: 2025-09-14 19:35:32
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 哈希值计算工具类
 * 
 * @author yourname
 * @version 1.0
 */
public class HashCalculator {

    private static final String HASH_ALGORITHM = "SHA-256"; // 使用SHA-256哈希算法

    /**
     * 计算字符串的哈希值
     * 
     * @param input 输入字符串
     * @return 哈希值
     */
    public static String calculateHash(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("输入字符串不能为空");
        }

        try {
            // 获取哈希算法实例
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            // 计算哈希值
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            // 将哈希值转换为Base64编码的字符串
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("哈希算法不支援", e);
        }
    }

    public static void main(String[] args) {
        try {
            String input = "Hello World";
            String hashValue = calculateHash(input);
            System.out.println("哈希值: " + hashValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
