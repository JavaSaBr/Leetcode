package javasabr.leetcode.array;

import java.util.*;

public class ThreeSum {

    public static void main(String[] args) {
        System.out.println(baseline(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(baseline(new int[]{0, 1, 1}));
        System.out.println(baseline(new int[]{0, 0, 0}));
        System.out.println(baseline(new int[]{0, 0, 0, 0}));
        System.out.println(baseline(new int[]{-1,0,1,2,-1,-4}));
    }

    public List<List<Integer>> threeSum(int[] nums) {


        return List.of();
    }

    public static List<List<Integer>> baseline(int[] nums) {

        var result = new HashSet<Triple>();
        var found = new HashMap<Integer, Triple>();

        for (int i = 0; i < nums.length; i++) {

            int first = nums[i];
            boolean nextIteration = false;

            var iFound = found.get(i);

            for (int j = 0; j < nums.length && !nextIteration; j++) {

                if (j == i) {
                    continue;
                } else if (iFound != null && iFound.contains(i) && iFound.contains(j)) {
                    continue;
                }

                var jFound = found.get(j);

                if (jFound != null && jFound.contains(i) && jFound.contains(j)) {
                    continue;
                }

                int second = nums[j];

                for (int k = 0; k < nums.length; k++) {

                    if (k == i || k == j) {
                        continue;
                    }

                    int third = nums[k];

                    if (first + second + third != 0) {
                        continue;
                    }

                    int minIndex = Math.min(Math.min(i, j), k);
                    int maxIndex = Math.max(Math.max(i, j), k);

                    int middleIndex;

                    if (i > minIndex && i < maxIndex) {
                        middleIndex = i;
                    } else if (j > minIndex && j < maxIndex) {
                        middleIndex = j;
                    } else {
                        middleIndex = k;
                    }

                    found.put(minIndex, )
                    result.add(new Triple(minIndex, middleIndex, maxIndex));
                    nextIteration = true;
                    break;
                }
            }
        }

        System.out.println(result);

        return result
                .stream()
                .map(indexes -> List.of(nums[indexes.a], nums[indexes.b], nums[indexes.c]))
                .toList();
    }

    private record Triple(int a, int b, int c) {
        boolean contains(int value) {
            return a == value || b == value || c == value;
        }
    }
}
