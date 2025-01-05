package javasabr.leetcode.search;

import java.util.Arrays;

public class ManachersAlgorithm {

  public static void main(String[] args) {

    String text1 = "babcbabcbaccba";
    System.out.println(findLongestPalindromeV1(text1));
    System.out.println(findLongestPalindromeV2(text1));
    System.out.println(findLongestPalindromeV3(text1));

    String text2 = "abaaba";
    System.out.println(findLongestPalindromeV1(text2));
    System.out.println(findLongestPalindromeV2(text2));
    System.out.println(findLongestPalindromeV3(text2));

    String text3 = "abababa";
    System.out.println(findLongestPalindromeV1(text3));
    System.out.println(findLongestPalindromeV2(text3));
    System.out.println(findLongestPalindromeV3(text3));

    String text4 = "abcbabcbabcba";
    System.out.println(findLongestPalindromeV1(text4));
    System.out.println(findLongestPalindromeV2(text4));
    System.out.println(findLongestPalindromeV3(text4));

    String text5 = "forgeeksskeegfor";
    System.out.println(findLongestPalindromeV1(text5));
    System.out.println(findLongestPalindromeV2(text5));
    System.out.println(findLongestPalindromeV3(text5));

    String text6 = "caba";
    System.out.println(findLongestPalindromeV1(text6));
    System.out.println(findLongestPalindromeV2(text6));
    System.out.println(findLongestPalindromeV3(text6));

    String text7 = "abacdfgdcaba";
    System.out.println(findLongestPalindromeV1(text7));
    System.out.println(findLongestPalindromeV2(text7));
    System.out.println(findLongestPalindromeV3(text7));

    String text8 = "abacdfgdcabba";
    System.out.println(findLongestPalindromeV1(text8));
    System.out.println(findLongestPalindromeV2(text8));
    System.out.println(findLongestPalindromeV3(text8));

    String text9 = "abacdedcaba";
    System.out.println(findLongestPalindromeV1(text9));
    System.out.println(findLongestPalindromeV2(text9));
    System.out.println(findLongestPalindromeV3(text9));
  }

  public static String findLongestPalindromeV1(String text) {

    int textLength = text.length();
    if (textLength == 0) {
      return null;
    }

    // Position count
    int indexesLength = 2 * textLength + 1;

    char[] chars = new char[indexesLength];
    chars[0] = '|';

    // transform: "abcdcba" -> "|a|b|c|d|c|b|a|
    for(int i = 0, j = 1; i < textLength; i++) {
      chars[j++] = text.charAt(i);
      chars[j++] = '|';
    }

    // LPS Length Array
    // example: "|a|b|c|d|c|b|a|" -> "[0,1,0,1,0,3,0,7,0,3,0,1,0,1,0]
    // index position -> length of palindrome
    int[] indexes = new int[indexesLength];
    indexes[0] = 0; // always 0
    indexes[1] = 1; // always 1
    indexes[indexesLength - 2] = 1; // always 1
    indexes[indexesLength - 1] = 0; // always 0

    int longest = 1;

    for (int i = 2, limit = indexesLength - 3; i < limit; i++) {
      int length = recurPalindromeLength(chars, i);
      indexes[i] = length;
      longest = Math.max(longest, length);
    }

    /*
    [|, b, |, a, |, b, |, c, |, b, |, a, |, b, |, c, |, b, |, a, |, c, |, c, |, b, |, a, |]
    [0, 1, 0, 3, 0, 1, 0, 7, 0, 1, 0, 9, 0, 1, 0, 5, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0]
     */

    String result = extractResult(chars, indexes, longest);

    if (result != null) {
      return result;
    }

    return text.substring(0, 1);
  }

  public static String findLongestPalindromeV2(String text) {

    int textLength = text.length();
    if (textLength == 0) {
      return null;
    }

    // Position count
    int indexesLength = 2 * textLength + 1;

    char[] chars = new char[indexesLength];
    chars[0] = '|';

    // transform: "abcdcba" -> "|a|b|c|d|c|b|a|
    for(int i = 0, j = 1; i < textLength; i++) {
      chars[j++] = text.charAt(i);
      chars[j++] = '|';
    }

    // LPS Length Array
    // example: "|a|b|c|d|c|b|a|" -> "[0,1,0,1,0,3,0,7,0,3,0,1,0,1,0]
    // index position -> length of palindrome
    int[] indexes = new int[indexesLength];
    indexes[0] = 0; // always 0
    indexes[1] = 1; // always 1
    indexes[indexesLength - 2] = 1; // always 1
    indexes[indexesLength - 1] = 0; // always 0

    int longest = 1;
    int rightLimit = indexesLength - 1;

    for (int centerPosition = 1, centerLimit = indexesLength - 2; centerPosition < centerLimit;) {

      int centerLength = indexes[centerPosition];
      int centerRightPosition = centerPosition + centerLength;
      int currentRightPosition = centerPosition + 1;

      // should just calculate next palindrome
      if (currentRightPosition > centerRightPosition) {
        int length = recurPalindromeLength(chars, currentRightPosition);
        indexes[currentRightPosition] = length;
        longest = Math.max(longest, length);
        centerPosition = currentRightPosition;
        continue;
      }

      while (currentRightPosition <= centerRightPosition) {

        int currentLeftPosition = (centerPosition * 2) - currentRightPosition;
        int leftLength = indexes[currentLeftPosition];

        int distanceBetweenRights = centerRightPosition - currentRightPosition;

        if (leftLength < distanceBetweenRights) {
          indexes[currentRightPosition++] = leftLength;
          continue;
        }

        if (leftLength == distanceBetweenRights) {
          // if the center right position is end of the string
          if (centerRightPosition == rightLimit) {
            indexes[currentRightPosition++] = leftLength;
          } else if (centerRightPosition < rightLimit) {
            // we use leftLength as start position for calculating palindrome length
            int length = expandPalindromeLength(chars, currentRightPosition, leftLength);
            indexes[currentRightPosition++] = length;
            longest = Math.max(longest, length);
          } else {
            int length = recurPalindromeLength(chars, currentRightPosition);
            indexes[currentRightPosition++] = length;
            longest = Math.max(longest, length);
          }
          continue;
        }

        // we use distanceBetweenRights as start position for calculating palindrome length
        int length = expandPalindromeLength(chars, currentRightPosition, distanceBetweenRights);
        indexes[currentRightPosition++] = length;
        longest = Math.max(longest, length);
      }

      centerPosition = currentRightPosition - 1;
    }

    //System.out.println(Arrays.toString(chars));
    //System.out.println(Arrays.toString(indexes));

    String result = extractResult(chars, indexes, longest);
    if (result != null) {
      return result;
    }

    return text.substring(0, 1);
  }

  public static String findLongestPalindromeV3(String text) {

    int textLength = text.length();
    if (textLength == 0) {
      return null;
    }

    // Position count
    int indexesLength = 2 * textLength + 1;

    char[] chars = new char[indexesLength];
    chars[0] = '|';

    // transform: "abcdcba" -> "|a|b|c|d|c|b|a|
    for(int i = 0, j = 1; i < textLength; i++) {
      chars[j++] = text.charAt(i);
      chars[j++] = '|';
    }

    // LPS Length Array
    // example: "|a|b|c|d|c|b|a|" -> "[0,1,0,1,0,3,0,7,0,3,0,1,0,1,0]
    // index position -> length of palindrome
    int[] indexes = new int[indexesLength];
    indexes[0] = 0; // always 0
    indexes[1] = 1; // always 1
    indexes[indexesLength - 2] = 1; // always 1
    indexes[indexesLength - 1] = 0; // always 0

    int longest = 1;
    int rightLimit = indexesLength - 1;

    for (int centerPosition = 1, centerLimit = indexesLength - 2; centerPosition < centerLimit;) {

      int centerLength = indexes[centerPosition];
      int centerRightPosition = centerPosition + centerLength;
      int currentRightPosition = centerPosition + 1;

      // should just calculate next palindrome
      if (currentRightPosition > centerRightPosition) {
        int length = recurPalindromeLength(text, currentRightPosition);
        indexes[currentRightPosition] = length;
        longest = Math.max(longest, length);
        centerPosition = currentRightPosition;
        continue;
      }

      while (currentRightPosition <= centerRightPosition) {

        int currentLeftPosition = (centerPosition * 2) - currentRightPosition;
        int leftLength = indexes[currentLeftPosition];

        int distanceBetweenRights = centerRightPosition - currentRightPosition;

        if (leftLength < distanceBetweenRights) {
          indexes[currentRightPosition++] = leftLength;
          continue;
        }

        if (leftLength == distanceBetweenRights) {
          // if the center right position is end of the string
          if (centerRightPosition == rightLimit) {
            indexes[currentRightPosition++] = leftLength;
          } else if (centerRightPosition < rightLimit) {
            // we use leftLength as start position for calculating palindrome length
            int length = expandPalindromeLength(text, currentRightPosition, leftLength);
            indexes[currentRightPosition++] = length;
            longest = Math.max(longest, length);
          } else {
            int length = recurPalindromeLength(text, currentRightPosition);
            indexes[currentRightPosition++] = length;
            longest = Math.max(longest, length);
          }
          continue;
        }

        // we use distanceBetweenRights as start position for calculating palindrome length
        int length = expandPalindromeLength(text, currentRightPosition, distanceBetweenRights);
        indexes[currentRightPosition++] = length;
        longest = Math.max(longest, length);
      }

      centerPosition = currentRightPosition - 1;
    }

    //System.out.println(Arrays.toString(indexes));

    String result = extractResult(text, indexes, longest);
    if (result != null) {
      return result;
    }

    return text.substring(0, 1);
  }

  private static int recurPalindromeLength(char[] chars, int center) {
    if (chars[center] == '|') {
      return expandPalindromeLength(chars, center, 0);
    } else {
      return expandPalindromeLength(chars, center, 1);
    }
  }

  private static int recurPalindromeLength(String text, int center) {
    if (center % 2 == 0) {
      return expandPalindromeLength(text, center, 0);
    } else {
      return expandPalindromeLength(text, center, 1);
    }
  }

  private static int recurPalindromeLength(char[] chars, int center, int level) {

    int leftIndex = center - level;
    int rightIndex = center + level;

    if (leftIndex < 0 || rightIndex >= chars.length) {
      return 0;
    }

    char left = chars[leftIndex];
    char right = chars[rightIndex];

    if (left != right) {
      return 0;
    }

    return 2 + recurPalindromeLength(chars, center, level + 2);
  }

  private static int recurPalindromeLength(String text, int center, int level) {

    int leftIndex = center - level;

    if (leftIndex < 0) {
      return 0;
    }

    int rightIndex = (center + level) / 2;
    if (rightIndex >= text.length()) {
      return 0;
    }

    leftIndex /= 2;

    char left = text.charAt(leftIndex);
    char right = text.charAt(rightIndex);

    if (left != right) {
      return 0;
    }

    return 2 + recurPalindromeLength(text, center, level + 2);
  }

  private static int expandPalindromeLength(char[] chars, int center, int initLevel) {
    return initLevel + recurPalindromeLength(chars, center, 1 + initLevel);
  }

  private static int expandPalindromeLength(String text, int center, int initLevel) {
    return initLevel + recurPalindromeLength(text, center, 1 + initLevel);
  }

  private static String extractResult(char[] chars, int[] indexes, int longest) {
    for (int i = 0, length = indexes.length; i < length; i++) {

      int palindromeLength = indexes[i];
      if (palindromeLength != longest) {
        continue;
      }

      var builder = new StringBuilder(palindromeLength);

      for (int j = i - palindromeLength + 1, limit = i + palindromeLength; j < limit; j += 2) {
        builder.append(chars[j]);
      }

      return builder.toString();
    }
    return null;
  }

  private static String extractResult(String text, int[] indexes, int longest) {
    for (int i = 0, length = indexes.length; i < length; i++) {

      int palindromeLength = indexes[i];
      if (palindromeLength != longest) {
        continue;
      }

      var builder = new StringBuilder(palindromeLength);
      int center = (i / 2);
      int start = center - (palindromeLength / 2);

      for (int j = start, limit = start + palindromeLength; j < limit; j++) {
        builder.append(text.charAt(j));
      }

      return builder.toString();
    }
    return null;
  }
}
