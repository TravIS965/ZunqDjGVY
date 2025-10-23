// 代码生成时间: 2025-10-24 07:42:43
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SortAlgorithmApp {

    /*
     * Method to sort an array of integers using Bubble Sort algorithm
     * @param arr The array of integers to be sorted
     * @return The sorted array of integers
     */
    public static int[] bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is null or empty");
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < (arr.length - i); j++) {
                if (arr[j - 1] > arr[j]) {
                    // Swap elements
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /*
     * Method to sort a list of objects using Collections.sort with a custom comparator
     * @param list The list of objects to be sorted
     * @param comparator The custom comparator for sorting
     * @param <T> The type of objects in the list
     * @return The sorted list of objects
     */
    public static <T> List<T> customSort(List<T> list, Comparator<T> comparator) {
        if (list == null || comparator == null) {
            throw new IllegalArgumentException("List or comparator is null");
        }

        Collections.sort(list, comparator);
        return list;
    }

    /*
     * Main method to demonstrate the sorting algorithms
     */
    public static void main(String[] args) {
        try {
            int[] intArray = {5, 3, 8, 4, 2};
            System.out.println("Before Bubble Sort: " + Arrays.toString(intArray));
            intArray = bubbleSort(intArray);
            System.out.println("After Bubble Sort: " + Arrays.toString(intArray));

            List<String> stringList = new ArrayList<>(Arrays.asList("banana", "apple", "orange", "mango"));
            System.out.println("Before Custom Sort: " + stringList);
            stringList = customSort(stringList, String::compareToIgnoreCase);
            System.out.println("After Custom Sort: " + stringList);

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
