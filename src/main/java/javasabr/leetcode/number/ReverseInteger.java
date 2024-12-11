package javasabr.leetcode.number;

public class ReverseInteger {

    public static void main(String[] args) {
        System.out.println(ReverseInteger.reverse(123));
        System.out.println(ReverseInteger.reverse(-123));
        System.out.println(ReverseInteger.reverse(120));
        System.out.println(ReverseInteger.reverse(1534236469));
    }

    public static int reverse(int x) {

        long reverse = 0;
        long temp = x < 0 ? -x : x;

        while (temp != 0) {
            long rightNumber = temp % 10;
            long reversedWithOffset = reverse * 10;
            reverse = reversedWithOffset + rightNumber;
            temp = temp / 10;
        }

        if (reverse > Integer.MAX_VALUE) {
            return 0;
        } else if (reverse < Integer.MIN_VALUE) {
            return 0;
        }

        return (int) (x < 0 ? -reverse : reverse);
    }
}
