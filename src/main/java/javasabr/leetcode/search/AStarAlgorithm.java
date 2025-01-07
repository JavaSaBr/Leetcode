package javasabr.leetcode.search;

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

    PointNode initNode = new PointNode(start);
    initNode.distance = initNode.position.squaredDistance(end);
    initNode.cost = 0;

    var close = new ArrayList<PointNode>();
    var open = new ArrayList<PointNode>();
    open.addLast(initNode);

    boolean ready = false;

    while (!open.isEmpty()) {
      PointNode next = open.removeFirst();
      close.addLast(next);

      if (fillOpen(map, next, end, open, close)) {
        ready = true;
        break;
      }
    }

    if (!ready) {
      return List.of();
    }

    PointNode prevPos = close.getLast();

    var path = new ArrayList<Point2Int>();
    path.add(end);
    path.add(prevPos.position);

    var parent = prevPos.parent;

    for (int i = close.size() - 2; i >= 0; i--) {
      prevPos = close.get(i);
      if (prevPos.position.equals(parent) && !prevPos.position.equals(start)) {
        path.add(prevPos.position);
        parent = prevPos.parent;
      }
    }

    path.add(start);
    return path;
  }

  private static boolean fillOpen(
      PointsMap map,
      PointNode node,
      Point2Int dest,
      Collection<PointNode> open,
      Collection<PointNode> closed) {

    for (int x = 0; x < 8; x++) {
      // for diagonals
      int stepCost = x < 4 ? 1 : 1;
      Point2Int neighbour = node.position.add(map.neighbours[x]);

      if (neighbour.equals(dest)) {
        return true;
      } else if (!map.isValid(neighbour) || map.isBlocked(neighbour)) {
        continue;
      }

      int nextCost = stepCost + node.cost;
      int dist = neighbour.squaredDistance(dest);

      if (existPoint(neighbour, nextCost + dist, open, closed)) {
        continue;
      }

      var nextNode = new PointNode(neighbour);
      nextNode.parent = node.position;
      nextNode.cost = nextCost;
      nextNode.distance = dist;

      open.add(nextNode);
    }

    return false;
  }

  private static boolean existPoint(
      Point2Int point,
      int cost,
      Collection<PointNode> opened,
      Collection<PointNode> closed) {

    PointNode closedNode = closed
        .stream()
        .filter(node -> node.position.equals(point))
        .findFirst()
        .orElse(null);

    if (closedNode != null) {
      if (closedNode.cost + closedNode.distance < cost) {
        return true;
      }
      closed.remove(closedNode);
      return false;
    }

    PointNode openedNode = opened
        .stream()
        .filter(node -> node.position.equals(point))
        .findFirst()
        .orElse(null);

    if (openedNode != null) {
      if (openedNode.cost + openedNode.distance < cost) {
        return true;
      }
      opened.remove(openedNode);
    }

    return false;
  }

  private static class PointsMap {

    final Point2Int[] neighbours = {
        point(-1, -1), point(1, -1),
        point(-1, 1), point(1, 1),
        point(0, -1), point(-1, 0),
        point(0, 1), point(1, 0)
    };

    final int[][] map;
    final int width, height;

    PointsMap(int[][] map, int width, int height) {
      this.map = new int[width][height];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          this.map[x][y] = map[y][x];
        }
      }
      this.width = width;
      this.height = height;
    }

    boolean isValid(Point2Int point) {
      return point.x() > -1 && point.y() > -1 && point.x() < width && point.y() < height;
    }

    boolean isBlocked(Point2Int point) {
      return map[point.x()][point.y()] == 1;
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
