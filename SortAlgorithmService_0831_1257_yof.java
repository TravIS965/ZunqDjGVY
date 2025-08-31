// 代码生成时间: 2025-08-31 12:57:44
public class SortAlgorithmService {

    // 希尔排序实现
    public void shellSort(int[] arr) {
        int n = arr.length;
        // 计算增量
        int gap = n / 2;
        while (gap > 0) {
            for (int i = gap; i < n; ++i) {
                int temp = arr[i];
                int j;
                // 对于每个元素，将其插入到已排序序列中的正确位置
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
            gap /= 2;
        }
    }

    // 冒泡排序实现
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换 arr[j+1] 和 arr[j] 的位置
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // 快速排序实现
    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // 快速排序划分逻辑
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // 获取排序结果
    public int[] getSortedArray(int[] arr, String algorithm) {
        if (algorithm == null || arr == null) {
            throw new IllegalArgumentException("Algorithm and array cannot be null");
        }
        try {
            switch (algorithm.toLowerCase()) {
                case "shell":
                    shellSort(arr);
                    break;
                case "bubble":
                    bubbleSort(arr);
                    break;
                case "quick":
                    quickSort(arr, 0, arr.length - 1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sorting algorithm. Please choose from shell, bubble, quick.");
            }
        } catch (Exception e) {
            // 错误处理
            System.err.println("Error during sorting: " + e.getMessage());
            return null;
        }
        return arr;
    }

    // 主函数，用于测试排序算法
    public static void main(String[] args) {
        SortAlgorithmService service = new SortAlgorithmService();
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        String algorithm = "shell"; // 可以更换为 bubble 或 quick 测试不同排序算法
        try {
            int[] sortedArray = service.getSortedArray(array, algorithm);
            System.out.println("Sorted array using " + algorithm + " sort");
            for (int value : sortedArray) {
                System.out.print(value + " ");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}