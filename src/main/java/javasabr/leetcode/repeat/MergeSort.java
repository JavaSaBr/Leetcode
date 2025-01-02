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

  public static int[] sort(int[] input) {

    int[] copy = Arrays.copyOf(input, input.length);

    sort(copy, 0, copy.length - 1);

    return copy;
  }

  private static void sort(int[] array, int low, int high) {

    if (low >= high) {
      return;
    }

    int mid = low + (high - low) / 2;

    sort(array, low, mid);
    sort(array, mid + 1, high);
    merge(array, low, mid, high);
  }

  private static void merge(int[] array, int low, int mid, int high) {

    int leftLength = mid - low + 1;
    int rightLength = high - mid;

    int[] left = new int[leftLength];
    int[] right = new int[rightLength];

    System.arraycopy(array, low, left, 0, leftLength);
    System.arraycopy(array, mid + 1, right, 0, rightLength);

    int leftIndex = 0, rightIndex = 0, index = low;

    while (leftIndex < leftLength && rightIndex < rightLength) {
      if (left[leftIndex] < right[rightIndex]) {
        array[index++] = left[leftIndex++];
      } else {
        array[index++] = right[rightIndex++];
      }
    }

    while (leftIndex < leftLength) {
      array[index++] = left[leftIndex++];
    }

    while (rightIndex < rightLength) {
      array[index++] = right[rightIndex++];
    }
  }
}
