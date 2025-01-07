package javasabr.leetcode;

public record Point2Int(int x, int y) {

  public static Point2Int point(int x, int y) {
    return new Point2Int(x, y);
  }

  public int distance(Point2Int another) {
    int xDiff = another.x - x;
    int yDiff = another.y - y;
    return (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
  }

  public int squaredDistance(Point2Int another) {
    int xDiff = another.x - x;
    int yDiff = another.y - y;
    return xDiff * xDiff + yDiff * yDiff;
  }

  public Point2Int add(Point2Int another) {
    return new Point2Int(x + another.x, y + another.y);
  }

  @Override
  public String toString() {
    return "[" + x + "," + y + ']';
  }
}
