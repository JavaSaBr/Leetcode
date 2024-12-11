package javasabr.leetcode.structure;

public class ContainerWithMostWater {

    public static void main(String[] args) {
        System.out.println(optimize1(new int[] {1,8,6,2,5,4,8,3,7}));
        System.out.println(baseline(new int[] {8361,5302,8672,2400,5150,3527,9216,6713,2902,310,555,9176,311,9968,5705,3983,7992,8553,6953,9541,5828,1750,6731,3552,5274,7303,3724,5387,9504,1900,937,1146,7266,7943,7911,9055,8046,7180,6516,7810,686,5210,1956,4540,7540,2083,1579,4260,2450,2527,6524,5723,6766,777,5694,6018,2880,3653,6011,8172,5943,2862,6594,2902,9887,5878,3065,8197,9195,4560,3428,2209,475,852,9488,3368,4319,6230,1975,5829,9474,4490,2067,6048,9136,5344,6022,1787,5553,140,5130,524,3450,4008,721,6154,5598,8219,4614,3404,8232,9023,4552,7711,6057,5324,8578,3595,4663,4,3703,1429,7921,3085,3694,1461,8932,2632,7046,801,6043,617,7565,3469,1627,1464,3050,7982,6702,5467,8604,5515,9155,3260,5040,313,8885,929,4103,7947,1139,702,1047,2889,1439,3945,4738,2462,8491,7699,376,4639,1329,3644,7408,3665,7417,1388,861,7510,7908,4568,2618,4565,7222,2003,1586,9494,1744,7997,7389,9476,2752,701,5925,4963,6859,1634,7170,1336,1514,6757,698,5123,4390,7910,7527,9520,156,6402,1428,789,3411,106,3206,8216,700,994,337,9329,5310,7897,1462,5709,872,1482,3137,1197,6459,822,1715,6575,2697,8903,4315,2139,2295,7797,1060,3511,1564,6625,3579,6412,4178,4003,9431,4251,1147,3418,5180,8712,9484,2582,4408,1945,6068,1269,1464,7459,2953,9773,2621,9471,9295,7095,3236,3526,7493,2437,2845,9123,2062,7329,6133,7696,3930,8297,3552,4879,3535,2458,3488,4176,2415,4964,4239,7299,6820,9586,2081,6675,184,5397,2308,3539}));
    }

    public static int optimize1(int[] height) {

        int max = 0;
        int minHeight = 1;

        for (int f = 0, length = height.length, l = length - 1; f < l;) {

            int left = height[f];
            while (left < minHeight && f < l) {
                left = height[++f];
            }

            int right = height[l];
            while (right < minHeight && l > f) {
                right = height[--l];
            }

            int finalHeight = Math.min(left, right);
            int width = l - f;
            int result = finalHeight * width;

            if (result > max) {
                max = result;
            }

            if (finalHeight > minHeight) {
                minHeight = finalHeight;
            }

            if (left < right) {
                f++;
            } else {
                l--;
            }

            if (width == 1) {
                break;
            }
        }

        return max;
    }

    public static int baseline(int[] height) {

        int max = 0;

        for (int i = 0, length = height.length; i < length; i++) {

            int left = height[i];
            if (left < 1) {
                continue;
            }

            for (int j = 1; j < length; j++) {
                int right = height[j];

                if (right < 1) {
                    continue;
                }

                int width = j - i;
                int resultHeight = Math.min(left, right);
                int result = width * resultHeight;

                if (result > max) {
                    max = result;
                }
            }
        }

        return max;
    }

}
