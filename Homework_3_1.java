import java.util.Random;

public class Homework_3_1 {
    static int step = 0;

    public static void main(String[] args) {
        int n = 42; //note that n should always be >1

        //initial array
        int[] arr = getRandomArray(n);
        printArray(arr);

        //final array and missing number
        arr = popRandomInt(arr, n);
        int absent = findAbsent(arr, n);
        printArray(arr);
        System.out.println("\nAbsent number is:\t" + absent);
        System.out.println("Number was found in " + step + " steps");
    }

    private static int findAbsent (int[] arr, int n) {
        int low = 0;
        int high = n - 2;
        int mid;
        if (low + 1 != arr[low]) {
            step++;
            return low + 1;
        }
        while (low < n - 1 && low + 1 == arr[low]) {
            step++;
            mid = (low + high) / 2;
            if (arr[mid] == mid + 1) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low + 1;
    }

    private static int[] getRandomArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    private static void printArray(int[] arr) {
        System.out.print("{\t");
        for (int integer: arr) {
            System.out.print(integer + "\t");
        }
        System.out.println("}");
    }

    private static int[] popRandomInt (int[] arr, int n) {
        int index = new Random().nextInt(n);
        int[] arrPopped = new int [n - 1];
        for (int i = 0, j = 0; i < n; i++ ) {
            if (i != index) {
                arrPopped[j++] = arr[i];
            }
        }
        return arrPopped;
    }
}
