package javasabr.leetcode.repeat;

import static javasabr.leetcode.Point2Int.point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javasabr.leetcode.Point2Int;

public class AStarAlgorithm {

  public static void main(String[] args) {

    int[][] mapData = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 1, 0},
        {0, 0, 1, 0, 0, 0, 1, 0},
        {0, 0, 1, 0, 0, 0, 1, 0},
        {0, 0, 1, 1, 1, 1, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}};

    PointsMap pointsMap = new PointsMap(mapData, 8 ,8);

    var source = new Point2Int(0, 0);
    var dest = new Point2Int(7, 7);

    System.out.println(search(pointsMap, source, dest));
  }

  public static List<Point2Int> search(PointsMap map, Point2Int start, Point2Int end) {

   return List.of();
  }

  private static class PointsMap {

    final int[][] map;
    final int width, height;

    PointsMap(int[][] map, int width, int height) {

      this.map = map;
      this.width = width;
      this.height = height;
    }
  }

  static class PointNode {

    Point2Int position, parent;
    int distance, cost;

    public PointNode(Point2Int position) {
      this.position = position;
    }

    @Override
    public String toString() {
      return "{" + position + "|" + parent + "|" + distance + "|" + cost + '}';
    }
  }
}
