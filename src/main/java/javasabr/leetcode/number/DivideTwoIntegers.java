package javasabr.leetcode.number;

public class DivideTwoIntegers {
    public static void main(String[] args) {
        //System.out.println(bitshiftBased(10, 3));
        //System.out.println(bitshiftBased(10, 3));
        //System.out.println(baseline(7, -3));
        //System.out.println(baseline(-12, -3));
        //System.out.println(bitshiftBased(155, 4));
        //System.out.println(baseline(-2147483648, -1));
        //System.out.println(baseline(2147483647, 2));
        //System.out.println(bitshiftBased(2147483647, 2));
        //System.out.println(bitshiftBased(-2147483648, 2));
        //System.out.println(bitshiftBased(-2147483648, 6));
        //System.out.println(bitshiftBased(-1006986286, -2145851451));
        //System.out.println(bitshiftBased(-1010369383, -2147483648));
        //System.out.println(bitshiftBased(1036963541, -24409858));
        //System.out.println(bitshiftBased(240, 10));
        //System.out.println(bitshiftBased(Integer.MIN_VALUE, 10));
        //System.out.println(bitshiftBased(1026117192, -874002063));
        System.out.println(bitshiftBased(-2147483648, -3));
    }

    public static int bitshiftBased(int dividend, int divisor) {

        int abs = Math.abs(dividend);
        int count = Math.abs(divisor);

        if (abs == Integer.MIN_VALUE) {
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
        }

        int result;

        if (count == 1) {
            result = abs;
        } else if (count == abs) {
            result = 1;
        } else if (abs >= 0 && count > abs) {
            return 0;
        } else if (count == Integer.MIN_VALUE) {
            return 0;
        } else {
            result = abs >= 0 ? findPositiveWithBitshift(count, abs) : findNegativeWithBitshift(count, abs);
        }

        if (dividend < 0 && divisor > 0 && result >= 0) {
            return -result;
        } else if (dividend >= 0 && divisor < 0 && result >= 0) {
            return -result;
        }

        return result;
    }

    private static int findPositiveWithBitshift(int divisor, int target) {

        int halfTarget = target >> 1;

        if (divisor == 2) {
            return halfTarget;
        } else if (halfTarget < divisor) {
            return 1;
        }

        int initValue = 1;
        int shiftTimes = 0;
        int initDivisor = 0;

        if (divisor % 2 == 0) {

            int times = 1;
            int nextDivisor = 2;

            while (nextDivisor < divisor) {
                times++;
                nextDivisor = nextDivisor << 1;
            }

            if (nextDivisor == divisor) {
                return target >> times;
            }

            initValue = target >> times;
            shiftTimes = times - 1;
            initDivisor = 2 << (shiftTimes - 1);
        }

        int prevSum = 0;

        for (int val = initValue;; val++) {

            int sum = shiftTimes == 0 ? 0 : val << shiftTimes;

            for (int i = initDivisor; i < divisor; i++) {
                sum += val;
            }

            if (sum < prevSum) {
                return val - 1;
            }

            prevSum = sum;

            if (sum > target) {
                return val - 1;
            } else if (sum == target) {
                return val;
            }
        }
    }

    private static int findNegativeWithBitshift(int divisor, int target) {

        int halfTarget = target >> 1;

        if (divisor == 2) {
            return halfTarget;
        } else if (halfTarget < divisor) {
            return 1;
        }

        int initValue = 1;
        int shiftTimes = 0;
        int initDivisor = 0;

        if (divisor % 2 == 0) {

            int times = 1;
            int nextDivisor = 2;

            while (nextDivisor < divisor) {
                times++;
                nextDivisor = nextDivisor << 1;
            }

            if (nextDivisor == divisor) {
                return target >> times;
            }

            initValue = Math.abs(target >> times);
            shiftTimes = times - 1;
            initDivisor = 2 << (shiftTimes - 1);
        }

        int prevSum = 0;
        for (int val = initValue;; val++) {

            int sum = shiftTimes == 0 ? 0 : -(val << shiftTimes);

            for (int i = initDivisor; i < divisor; i++) {
                sum -= val;
            }

            if (sum > prevSum) {
                return val - 1;
            }

            prevSum = sum;

            if (sum < target) {
                return val - 1;
            } else if (sum == target) {
                return val;
            }
        }
    }

    public static int baseline(int dividend, int divisor) {

        int abs = Math.abs(dividend);
        int count = Math.abs(divisor);

        if (abs == Integer.MIN_VALUE) {
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
        }

        int result;

        if (count == 1) {
            result = abs;
        } else {
            result = abs >= 0 ? findPositive(count, abs) : findNegative(count, abs);
        }

        if (dividend < 0 && divisor > 0) {
            return -result;
        } else if (dividend >= 0 && divisor < 0) {
            return -result;
        }

        return result;
    }

    private static int findPositive(int count, int target) {
        int prevSum = 0;
        for (int val = 1;; val++) {

            int sum = 0;

            for (int i = 0; i < count; i++) {
                sum += val;
            }

            if (sum < prevSum) {
                return val - 1;
            }

            prevSum = sum;

            if (sum > target) {
                return val - 1;
            } else if (sum == target) {
                return val;
            }
        }
    }

    private static int findNegative(int count, int target) {
        int prevSum = 0;
        for (int val = 1;; val++) {

            int sum = 0;

            for (int i = 0; i < count; i++) {
                sum -= val;
            }

            if (sum > prevSum) {
                return val - 1;
            }

            prevSum = sum;

            if (sum < target) {
                return val - 1;
            } else if (sum == target) {
                return val;
            }
        }
    }
}
