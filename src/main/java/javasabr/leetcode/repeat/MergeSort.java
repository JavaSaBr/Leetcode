package javasabr.leetcode.repeat;

import java.util.Arrays;

public class MergeSort {

  public static void main(String[] args) {

    int[] input = {-54, 38, 0, 7, -40, -23, 75, 62, 90, -26, -105, 62, 80, 38};
    int[] sorted = Arrays.copyOf(input, input.length);
    Arrays.sort(sorted);

    System.out.println("STD:" + Arrays.toString(sorted));
    System.out.println("R:" + Arrays.toString(sort(input)));
  }

  private static int[] sort(int[] input) {

    int[] copy = Arrays.copyOf(input, input.length);

    int low = 0;
    int high = input.length - 1;
    int mid = low + (high - low) / 2;

    sort(copy, low, mid);
    sort(copy, mid + 1, high);

    merge(copy, low, mid, high);

    return copy;
  }

  private static void sort(int[] input, int low, int high) {

    if (low >= high) {
      return;
    }

    int mid = low + (high - low) / 2;

    sort(input, low, mid);
    sort(input, mid + 1, high);

    merge(input, low, mid, high);
  }

  private static void merge(int[] input, int low, int mid, int high) {

    int leftLength = mid - low + 1;
    int rightLength = high - mid;

    int[] leftArray = new int[leftLength];
    int[] rightArray = new int[rightLength];

    System.arraycopy(input, low, leftArray, 0, leftLength);
    System.arraycopy(input, mid + 1, rightArray, 0, rightLength);

    int leftIndex = 0, rightIndex = 0, k = low;

    while (leftIndex < leftLength && rightIndex < rightLength) {
      if (leftArray[leftIndex] <= rightArray[rightIndex]) {
        input[k++] = leftArray[leftIndex++];
      } else {
        input[k++] = rightArray[rightIndex++];
      }
    }

    while (leftIndex < leftLength) {
      input[k++] = leftArray[leftIndex++];
    }

    while (rightIndex < rightLength) {
      input[k++] = rightArray[rightIndex++];
    }
  }
}
