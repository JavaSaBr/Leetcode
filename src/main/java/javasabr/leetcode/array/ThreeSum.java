package javasabr.leetcode.array;

import java.util.*;

public class ThreeSum {

  public static void main(String[] args) {
    System.out.println("o1:" + optimize1(new int[]{-1,0,1,2,-1,-4}));
    System.out.println("b:" + baseline(new int[]{-1,0,1,2,-1,-4}));
    System.out.println(optimize1(new int[]{0,1,1}));
    System.out.println(optimize1(new int[]{0,0,0}));
    System.out.println(optimize1(new int[]{0,0,0,0}));
    System.out.println(optimize1(new int[]{-1,0,1, 2,-1,-4}));
    System.out.println(optimize1(new int[]{-2,0,0,2,2}));
    System.out.println(optimize1(new int[]{-1,0,1,2,-1,-4,-2,-3,3,0,4}));
    // [[-4,0,4],[-4,1,3],[-3,-1,4],[-3,0,3],[-3,1,2],[-2,-1,3],[-2,0,2],[-1,-1,2],[-1,0,1]]
    // [[-1, 0, 1], [1, 2, -3], [2, -2, 0], [-1, 3, -2], [-4, 4, 0]]
    // [[-1, 0, 1], [-1, 2, -1], [-1, -2, 3], [-1, -3, 4], [0, 2, -2], [0, -4, 4], [0, -3, 3], [1, 2, -3], [1, -4, 3]]
    // [[-1, 0, 1], [-1, 2, -1], [-1, -2, 3], [-1, -3, 4], [0, 2, -2], [0, -4, 4], [0, -3, 3], [1, 2, -3], [1, -4, 3]]

    // [-1,0,1] -> [-1, 0, 1]
    // [-1,-1,2] ->
    // [-2,0,2] -> [2, -2, 0]
    // [-2,-1,3] -> [-1, 3, -2]
    // [-3,1,2] -> [1, 2, -3]
    // [-3,0,3] ->
    // [-3,-1,4] ->
    // [-4,1,3] ->
    // [-4,0,4] -> [-4, 4, 0]
  }

  public List<List<Integer>> threeSum(int[] nums) {

    return List.of();
  }

  // [-1,0,1,2,-1,-4,-2,-3,3,0,4]
  //[[-1, 0, 1], [-1, 2, -1], [-1, -2, 3], [-1, -3, 4], [0, 2, -2], [0, -4, 4], [0, -3, 3], [1, 2, -3], [1, -4, 3]]

  // [{-1},0,1,2,-1,-4,-2,-3,3,0,4]
  // [-1,{0},1,2,-1,-4,-2,-3,3,0,4]
  // [-1,0,{1},2,-1,-4,-2,-3,3,0,4]

  // [{-1},0,1,2,-1,-4,-2,-3,3,0,4]
  // [-1,0,1,{2},-1,-4,-2,-3,3,0,4]
  // [{-1},0,1,2,-1,-4,-2,-3,3,0,4]

  // [{-1},0,1,2,-1,-4,-2,-3,3,0,4]
  // [-1,0,1,2,-1,-4,{-2},-3,3,0,4]
  // [-1,0,1,2,-1,-4,-2,-3,{3},0,4]

  // [{-1},0,1,2,-1,-4,-2,-3,3,0,4]
  // [-1,0,1,2,-1,-4,-2,{-3},3,0,4]
  // [-1,0,1,2,-1,-4,-2,-3,3,0,{4}]
  //-------------------------------
  // [-1,{0},1,2,-1,-4,-2,-3,3,0,4]
  // [-1,0,1,{2},-1,-4,-2,-3,3,0,4]
  // [-1,0,1,2,-1,-4,{-2},-3,3,0,4]

  // [-1,{0},1,2,-1,-4,-2,-3,3,0,4]
  // [-1,0,1,2,-1,{-4},-2,-3,3,0,4]
  // [-1,0,1,2,-1,-4,-2,-3,3,0,{4}]

  // [-1,{0},1,2,-1,-4,-2,-3,3,0,4]
  // [-1,0,1,2,-1,-4,-2,{-3},3,0,4]
  // [-1,0,1,2,-1,-4,-2,-3,{3},0,4]

  // [-1,0,{1},2,-1,-4,-2,-3,3,0,4]
  // [-1,0,1,{2},-1,-4,-2,-3,3,0,4]
  // [-1,0,1,2,-1,-4,-2,{-3},3,0,4]

  public static List<List<Integer>> optimize1(int[] nums) {

    var result = new ArrayList<List<Integer>>();
    var usedPairs = new HashMap<Integer, Set<Integer>>();
    var valueToIndex = new HashMap<Integer, Integer>();

    for (int i = 0; i < nums.length; i++) {

      int first = nums[i];

      Set<Integer> iPairs = usedPairs.get(i);

      valueToIndex.put(first, i);

      for (int j = i + 1; j < nums.length; j++) {

        int second = nums[j];

        if (iPairs != null && iPairs.contains(second)) {
          continue;
        }

        Set<Integer> jPairs = usedPairs.get(second);
        if (jPairs != null && jPairs.contains(first)) {
          continue;
        }

        int diff = -(first + second);

        for (int k = valueToIndex.getOrDefault(diff, 0); k < nums.length; k++) {

          if (k == i || k == j) {
            continue;
          }

          int third = nums[k];

          if (iPairs != null && iPairs.contains(third)) {
            continue;
          } else if (jPairs != null && jPairs.contains(third)) {
            continue;
          }

          Set<Integer> kPairs = usedPairs.get(third);
          if (kPairs != null && (kPairs.contains(first) || kPairs.contains(second))) {
            continue;
          }

          if (first + second + third != 0) {
            continue;
          }

          iPairs = usedPairs.computeIfAbsent(first, _ -> new HashSet<>());
          iPairs.add(second);
          iPairs.add(third);

          jPairs = usedPairs.computeIfAbsent(second, _ -> new HashSet<>());
          jPairs.add(first);
          jPairs.add(third);

          kPairs = usedPairs.computeIfAbsent(third, _ -> new HashSet<>());
          kPairs.add(first);
          kPairs.add(second);

          //System.out.printf("<%s-%s-%s>%n", i, j, k);

          result.add(List.of(first, second, third));
          break;
        }
      }
    }

    return result;
  }

  public static List<List<Integer>> baseline(int[] nums) {

    var result = new ArrayList<List<Integer>>();
    var usedPairs = new HashMap<Integer, Set<Integer>>();

    for (int i = 0; i < nums.length; i++) {

      int first = nums[i];

      Set<Integer> iPairs = usedPairs.get(i);

      for (int j = 0; j < nums.length; j++) {

        if (j == i) {
          continue;
        }

        int second = nums[j];

        if (iPairs != null && iPairs.contains(second)) {
          continue;
        }

        Set<Integer> jPairs = usedPairs.get(second);
        if (jPairs != null && jPairs.contains(first)) {
          continue;
        }

        for (int k = 0; k < nums.length; k++) {

          if (k == i || k == j) {
            continue;
          }

          int third = nums[k];

          if (iPairs != null && iPairs.contains(third)) {
            continue;
          } else if (jPairs != null && jPairs.contains(third)) {
            continue;
          }

          Set<Integer> kPairs = usedPairs.get(third);
          if (kPairs != null && (kPairs.contains(first) || kPairs.contains(second))) {
            continue;
          }

          if (first + second + third != 0) {
            continue;
          }

          iPairs = usedPairs.computeIfAbsent(first, _ -> new HashSet<>());
          iPairs.add(second);
          iPairs.add(third);

          jPairs = usedPairs.computeIfAbsent(second, _ -> new HashSet<>());
          jPairs.add(first);
          jPairs.add(third);

          kPairs = usedPairs.computeIfAbsent(third, _ -> new HashSet<>());
          kPairs.add(first);
          kPairs.add(second);

          result.add(List.of(first, second, third));
        }
      }
    }

    return result;
  }
}
