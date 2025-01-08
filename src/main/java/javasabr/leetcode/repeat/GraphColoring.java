package javasabr.leetcode.repeat;

import static javasabr.leetcode.Graph.Connections.node;
import static javasabr.leetcode.Graph.Connections.root;

import javasabr.leetcode.Graph;

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

    //System.out.println(requireColors(bigraph, 3));

    Graph<Integer> graph = Graph.with(
        root(0, 1, 2),
        node(1, 0, 2, 3),
        node(2, 0, 1, 4),
        node(3, 1, 4),
        root(4, 3, 2));

    //System.out.println(requireColors(graph, 10));
   // System.out.println(requireColors(graph, 2));
  }
}
