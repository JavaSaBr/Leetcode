package javasabr.leetcode.search;

import static javasabr.leetcode.Graph.Connections.node;
import static javasabr.leetcode.Graph.Connections.root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javasabr.leetcode.Graph;
import javasabr.leetcode.GraphNode;

public class GraphColoring {

  public static void main(String[] args) {

    Graph<Integer> bigraph = Graph.with(
        root(0, 1),
        node(1, 0, 2, 3),
        node(2, 1, 4),
        node(3, 1, 5),
        node(4, 2, 8),
        node(5, 3, 6),
        node(6, 5, 7),
        node(7, 6),
        node(8, 4, 9),
        node(9, 8));

   System.out.println(requireColors(bigraph, 3));

    Graph<Integer> graph = Graph.with(
        root(0, 1, 2),
        node(1, 0, 2, 3),
        node(2, 0, 1, 4),
        node(3, 1, 4),
        root(4, 3, 2));

    System.out.println(requireColors(graph, 10));
    System.out.println(requireColors(graph, 2));
  }

  public static int requireColors(Graph<Integer> graph, int limit) {

    var colorMap = new HashMap<GraphNode<Integer>, Integer>();
    var usedColors = new ArrayList<Integer>(limit);
    var excludeColors = new ArrayList<Integer>();

    for (GraphNode<Integer> root : graph.roots()) {
      if (colorMap.containsKey(root)) {
        continue;
      }
      if (!mark(root, colorMap, usedColors, excludeColors, limit)) {
        return -1;
      }
    }

    return usedColors.size();
  }

  private static boolean mark(
      GraphNode<Integer> graphNode,
      Map<GraphNode<Integer>, Integer> colorMap,
      List<Integer> usedColors,
      List<Integer> excludedColors,
      int limit) {

    boolean assigned = false;

    var connected = graphNode.connected();

    if (usedColors.isEmpty()) {
      usedColors.add(1);
      colorMap.put(graphNode, 1);
      assigned = true;
    } else {

      excludedColors.clear();

      for (GraphNode<Integer> anotherNode : connected) {
        Integer alreadyUsedColor = colorMap.get(anotherNode);
        if (alreadyUsedColor != null) {
          excludedColors.add(alreadyUsedColor);
        }
      }

      for (Integer color : usedColors) {
        if (excludedColors.contains(color)) {
          continue;
        }
        colorMap.put(graphNode, color);
        assigned = true;
        break;
      }
    }

    if (!assigned && usedColors.size() < limit) {
      int nextColor = usedColors.size() + 1;
      usedColors.add(nextColor);
      colorMap.put(graphNode, nextColor);
      assigned = true;
    }

    if (!assigned) {
      return false;
    }

    for (GraphNode<Integer> nextNode : connected) {

      if(colorMap.containsKey(nextNode)) {
        continue;
      }

      if (!mark(nextNode, colorMap, usedColors, excludedColors, limit)) {
        return false;
      }
    }

    return true;
  }
}
