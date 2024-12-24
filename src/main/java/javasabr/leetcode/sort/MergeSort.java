package javasabr.leetcode.sort;

import java.util.Arrays;

public class MergeSort {

  public static void main(String[] args) {
    int[] array = { 12, 11, 13, 5, 6, 7 };
    System.out.println(Arrays.toString(array));
    sort(array, 0, array.length - 1);
    System.out.println(Arrays.toString(array));
  }

  static void merge(int[] array, int left, int middle, int right) {

    int leftSize = middle - left + 1;
    int rightSize = right - middle;

    int[] leftSubArray = new int[leftSize];
    int[] rightSubArray = new int[rightSize];

    System.arraycopy(array, left, leftSubArray, 0, leftSize);
    System.arraycopy(array, middle + 1, rightSubArray, 0, rightSize);

    System.out.println("Marge:" + Arrays.toString(leftSubArray) + "><" + Arrays.toString(rightSubArray));

    int leftIndex = 0, rightIndex = 0;
    int sourceIndex = left;

    while (leftIndex < leftSize && rightIndex < rightSize) {
      if (leftSubArray[leftIndex] <= rightSubArray[rightIndex]) {
        array[sourceIndex] = leftSubArray[leftIndex];
        leftIndex++;
      } else {
        array[sourceIndex] = rightSubArray[rightIndex];
        rightIndex++;
      }
      sourceIndex++;
    }

    while (leftIndex < leftSize) {
      array[sourceIndex] = leftSubArray[leftIndex];
      leftIndex++;
      sourceIndex++;
    }
    while (rightIndex < rightSize) {
      array[sourceIndex] = rightSubArray[rightIndex];
      rightIndex++;
      sourceIndex++;
    }
  }

  public static void sort(int[] array, int start, int end) {

    if (start >= end) {
      return;
    }

    int middle = start + (end - start) / 2;

    sort(array, start, middle);
    sort(array, middle + 1, end);

    merge(array, start, middle, end);
  }
}
