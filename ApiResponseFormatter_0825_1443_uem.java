// 代码生成时间: 2025-08-25 14:43:03
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// ApiResponseFormatter类用于格式化API响应
public class ApiResponseFormatter {

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";

    private static final String RESPONSE_FORMAT = "{"status": "%s", "message": "%s", "data": %s}";

    // 格式化成功的响应
    public static String formatSuccessResponse(String message, Map<String, Object> data) {
        Objects.requireNonNull(message, "Message must not be null");
        Objects.requireNonNull(data, "Data must not be null");
        return String.format(RESPONSE_FORMAT, STATUS_SUCCESS, message, data);
    }

    // 格式化错误的响应
    public static String formatErrorResponse(String message, Map<String, Object> data) {
        Objects.requireNonNull(message, "Message must not be null");
        Objects.requireNonNull(data, "Data must not be null");
        return String.format(RESPONSE_FORMAT, STATUS_ERROR, message, data);
    }

    // 私有构造函数，防止实例化
    private ApiResponseFormatter() {}

    // 主方法，用于测试
    public static void main(String[] args) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("key", "value");
            System.out.println(formatSuccessResponse("Operation successful", data));

            Map<String, Object> errorData = new HashMap<>();
            errorData.put("errorKey", "errorValue");
            System.out.println(formatErrorResponse("Operation failed", errorData));
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
