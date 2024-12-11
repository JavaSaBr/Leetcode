package javasabr.leetcode.number;

public class PalindromeNumber {

    public static boolean isPalindrome(int x) {

        if (x < 0) {
            return false;
        } else if (x < 10) {
            return true;
        }

        if (x < 100_000_000) {
            int reverse = 0;
            int temp = x;
            while (temp != 0) {
                int rightNumber = temp % 10;
                int reversedWithOffset = reverse * 10;
                reverse = reversedWithOffset + rightNumber;
                temp = temp / 10;
            }

            return (reverse == x);
        }

        long reverse = 0;
        long temp = x;
        while (temp != 0) {
            long rightNumber = temp % 10;
            long reversedWithOffset = reverse * 10;
            reverse = reversedWithOffset + rightNumber;
            temp = temp / 10;
        }

        return (reverse == x);
    }
}
