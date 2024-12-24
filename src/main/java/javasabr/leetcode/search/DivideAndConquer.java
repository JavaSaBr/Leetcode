package javasabr.leetcode.search;

public class DivideAndConquer {

  public static void main(String[] args) {
    int[] array = {-5, 2, 50, 13, -10, 60, 43, 12, 7, 37};
    System.out.println(findMax(array, 0, array.length - 1));
  }

  public static int findMax(int[] array, int lower, int higher) {

    if (lower > higher) {
      return Integer.MIN_VALUE;
    } else if (lower == higher) {
      return array[lower];
    }

    int mid = (lower + higher) / 2;

    int leftMax = findMax(array, lower, mid);
    int rightMax = findMax(array, mid + 1, higher);

    return Math.max(leftMax, rightMax);
  }
}
