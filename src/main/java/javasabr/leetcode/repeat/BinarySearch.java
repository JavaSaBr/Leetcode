package javasabr.leetcode.repeat;

import java.util.Arrays;

public class BinarySearch {

  public static void main(String[] args) {
    int[] input = {1, 6, 11, 14, 17, 22, 26, 67, 153, 267, 281, 305, 374};
    System.out.println(Arrays.toString(input));
    System.out.println("STD[67]:" + Arrays.binarySearch(input, 67) + "<>R[67]:" + indexOf(input, 67));
    System.out.println("STD[67]:" + Arrays.binarySearch(input, 6) + "<>R[67]:" + indexOf(input, 6));
    System.out.println("STD[67]:" + Arrays.binarySearch(input, 374) + "<>R[67]:" + indexOf(input, 374));
    System.out.println("STD[67]:" + Arrays.binarySearch(input, 1) + "<>R[67]:" + indexOf(input, 1));
    int[] input2 = {1, 6, 11, 14, 17, 22, 26, 67, 84, 153, 267, 281, 305, 374};
    System.out.println(Arrays.toString(input2));
    System.out.println("STD[67]:" + Arrays.binarySearch(input2, 67) + "<>R[67]:" + indexOf(input2, 67));
    System.out.println("STD[67]:" + Arrays.binarySearch(input2, 6) + "<>R[67]:" + indexOf(input2, 6));
    System.out.println("STD[67]:" + Arrays.binarySearch(input2, 374) + "<>R[67]:" + indexOf(input2, 374));
    System.out.println("STD[67]:" + Arrays.binarySearch(input2, 1) + "<>R[67]:" + indexOf(input2, 1));
  }

  public static int indexOf(int[] array, int value) {

    int low = 0;
    int high = array.length - 1;
    int mid = low + ((high - low) / 2);

    while (low < high) {
      int midValue = array[mid];
      if (midValue > value) {
        high = mid;
        mid = low + ((high - low) / 2);
      } else if (midValue < value) {
        low = mid;
        mid = low + ((high - low) / 2) + 1;
      } else {
        return mid;
      }
    }

    return -1;
  }
}
