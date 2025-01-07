package javasabr.leetcode.search;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TravelingSalesmanProblem {

  public static void main(String[] args) {
    int[][] cost = { { 0, 10, 15, 20 },
                     { 10, 0, 35, 25 },
                     { 15, 35, 0, 30 },
                     { 20, 25, 30, 0 } };

    System.out.println(tsp(cost));
    System.out.println(recurTsp(cost));
    System.out.println(memoRecurTsp(cost));
  }

  public static int tsp(int[][] cost) {

    int numNodes = cost.length;
    List<Integer> nodes = new ArrayList<>();

    // 0 is always start
    for (int i = 1; i < numNodes; i++) {
      nodes.add(i);
    }

    int minCost = Integer.MAX_VALUE;

    // Generate all permutations of the remaining nodes
    do {

      int currentCost = 0;
      int currentNode = 0;

      for (int i = 0; i < nodes.size(); i++) {
        int nextNode = nodes.get(i);
        currentCost += cost[currentNode][nextNode];
        currentNode = nextNode;
      }

      // cost to return
      currentCost += cost[currentNode][0];
      minCost = Math.min(minCost, currentCost);

    } while (nextPermutation(nodes));

    return minCost;
  }

  private static boolean nextPermutation(List<Integer> nodes) {

    int i = nodes.size() - 2;

    while (i >= 0 && nodes.get(i) >= nodes.get(i + 1)) {
      i--;
    }

    if (i < 0) {
      return false;
    }

    int j = nodes.size() - 1;
    while (nodes.get(j) <= nodes.get(i)) {
      j--;
    }

    Collections.swap(nodes, i, j);
    Collections.reverse(nodes.subList(i + 1, nodes.size()));

    return true;
  }

  public static int recurTsp(int[][] cost) {
    return recurTotalCost(new HashSet<>(), 0, cost);
  }

  private static int recurTotalCost(Set<Integer> visited, int pos, int[][] cost) {

    // return to start city
    if (visited.size() == cost.length) {
      return cost[pos][0];
    }

    int ans = Integer.MAX_VALUE;

    // visit all combinations
    for (int i = 0; i < cost.length; i++) {
      if (!visited.contains(i)) {
        visited.add(i);
        ans = Math.min(ans, cost[pos][i] + recurTotalCost(visited, i, cost));
        visited.remove(i);
      }
    }

    return ans;
  }

  public static int memoRecurTsp(int[][] cost) {
    return memoRecurTotalCost(new HashSet<>(), 0, cost, new HashMap<>());
  }

  private static int memoRecurTotalCost(Set<Integer> visited, int pos, int[][] cost, Map<BitSet, Integer> memo) {

    // return to start city
    if (visited.size() == cost.length) {
      return cost[pos][0];
    }

    var mask = new BitSet(cost.length);
    for (Integer city : visited) {
      mask.set(city);
    }

    Integer result = memo.get(mask);
    if (result != null) {
      return result;
    }

    int ans = Integer.MAX_VALUE;

    // visit all combinations
    for (int i = 0; i < cost.length; i++) {
      if (!visited.contains(i)) {
        visited.add(i);
        ans = Math.min(ans, cost[pos][i] + memoRecurTotalCost(visited, i, cost, memo));
        visited.remove(i);
      }
    }

    memo.put(mask, ans);

    return ans;
  }
}
