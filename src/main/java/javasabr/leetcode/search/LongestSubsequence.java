package javasabr.leetcode.search;

import java.util.Arrays;

public class LongestSubsequence {

  public static void main(String[] args) {
    String s1 = "AGGTAB";
    String s2 = "GXTXAYB";

    System.out.println(lastCharRecursion(s1, s2));
    System.out.println(memorizedLastCharRecursion(s1, s2));
    System.out.println(dp2d(s1, s2));
    System.out.println(dp1d(s1, s2));
  }

  public static int lastCharRecursion(String leftString, String rightString) {
    return lastCharRecursion(leftString, rightString, leftString.length(), rightString.length());
  }

  private static int lastCharRecursion(String leftString, String rightString, int leftIndex, int rightIndex) {

    if (leftIndex == 0 || rightIndex == 0) {
      return 0;
    }

    if (leftString.charAt(leftIndex - 1) == rightString.charAt(rightIndex - 1)) {
      int withExcludedBoth = lastCharRecursion(leftString, rightString, leftIndex - 1, rightIndex - 1);
      return 1 + withExcludedBoth;
    } else {
      int withExcludedLeft = lastCharRecursion(leftString, rightString, leftIndex - 1, rightIndex);
      int withExcludedRight = lastCharRecursion(leftString, rightString, leftIndex, rightIndex - 1);
      return Math.max(withExcludedRight, withExcludedLeft);
    }
  }

  public static int memorizedLastCharRecursion(String leftString, String rightString) {

    int leftLength = leftString.length();
    int rightLength = rightString.length();

    int[][] indexes = new int[leftLength + 1][rightLength + 1];

    for (int i = 0; i <= leftLength; i++) {
      Arrays.fill(indexes[i], -1);
    }

    return memorizedLastCharRecursion(leftString, rightString, leftLength, rightLength, indexes);
  }

  private static int memorizedLastCharRecursion(
      String leftString,
      String rightString,
      int leftLength,
      int rightLength,
      int[][] indexes) {

    if (leftLength == 0 || rightLength == 0) {
      return 0;
    } else if (indexes[leftLength][rightLength] != -1) {
      return indexes[leftLength][rightLength];
    }

    if (leftString.charAt(leftLength - 1) == rightString.charAt(rightLength - 1)) {

      int resultWithExcludedBoth = memorizedLastCharRecursion(
          leftString,
          rightString,
          leftLength - 1,
          rightLength - 1, indexes);

      return indexes[leftLength][rightLength] = 1 + resultWithExcludedBoth;
    }

    int withExcludedLeft = memorizedLastCharRecursion(leftString, rightString, leftLength - 1, rightLength, indexes);
    int withExcludedRight = memorizedLastCharRecursion(leftString, rightString, leftLength, rightLength - 1, indexes);

    return indexes[leftLength][rightLength] = Math.max(withExcludedRight, withExcludedLeft);
  }

  public static int dp2d(String leftString, String rightString) {

    int leftLength = leftString.length();
    int rightLength = rightString.length();

    int[][] indexes = new int[leftLength + 1][rightLength + 1];

    for (int i = 1; i <= leftLength; i++) {
      for (int j = 1; j <= rightLength; j++) {
        // if it match takes the full prev + 1
        if (leftString.charAt(i - 1) == rightString.charAt(j - 1)) {
          indexes[i][j] = indexes[i - 1][j - 1] + 1;
        } else {
          // fi not takes max from prev column and prev row
          indexes[i][j] = Math.max(indexes[i - 1][j], indexes[i][j - 1]);
        }
      }
    }

    // the latest cell contains the max value
    return indexes[leftLength][rightLength];
  }

  public static int dp1d(String leftString, String rightString) {

    int leftLength = leftString.length();
    int rightLength = rightString.length();

    int[] prevRow = new int[rightLength + 1];
    int[] currentRow = new int[rightLength + 1];

    for (int i = 1; i <= leftLength; i++) {
      Arrays.fill(currentRow, 0);
      for (int j = 1; j <= rightLength; j++) {
        // if it match takes the full prev + 1
        if (leftString.charAt(i - 1) == rightString.charAt(j - 1)) {
          currentRow[j] = prevRow[j - 1] + 1;
        } else {
          // if not takes max from prev column and prev row
          currentRow[j] = Math.max(prevRow[j], currentRow[j - 1]);
        }
      }
      System.arraycopy(currentRow, 0, prevRow, 0, currentRow.length);
    }

    // the latest cell contains the max value
    return prevRow[rightLength];
  }
}
