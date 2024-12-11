package javasabr.leetcode.number;

public class StringToInt {

    public static void main(String[] args) {
        System.out.println(myAtoi("123"));
        System.out.println(myAtoi("64007"));
        System.out.println(myAtoi("42"));
        System.out.println(myAtoi(" -042"));
        System.out.println(myAtoi("1337c0d3"));
        System.out.println(myAtoi("0-1"));
        System.out.println(myAtoi("-91283472332"));
        System.out.println(myAtoi("+-12"));
        System.out.println(myAtoi("9223372036854775808"));
        System.out.println(myAtoi("  0000000000012345678"));
        System.out.println(myAtoi("  +  413"));
    }

    public static int myAtoi(String s) {

        int length = s.length();
        boolean positive = true;
        boolean started = false;

        long result = 0;

        for(int i = 0, digits = 0, symbols = 0, factor = 0; i < length; i++) {

            char next = s.charAt(i);
            int possibleInt = (int) next - 48;

            if (possibleInt > -1 && possibleInt < 10) {
                digits++;

                if (possibleInt != 0) {
                    started = true;
                }

                if (started) {
                    factor++;
                }

                if (factor > 10) {
                    return positive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                } else {
                    long offset = result * 10;
                    result = offset + possibleInt;
                }

                continue;
            }

            if (next == ' ' && digits == 0 && symbols == 0) {
                continue;
            }

            if (next == '+' || next == '-') {
                symbols++;
                if (digits == 0 && symbols < 2) {
                    positive = next != '-';
                    continue;
                } else {
                    // not digit char
                    break;
                }
            }

            // not digit char
            break;
        }

        if (!positive) {
            result = -result;
        }

        if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else if (result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        return (int) result;
    }
}
