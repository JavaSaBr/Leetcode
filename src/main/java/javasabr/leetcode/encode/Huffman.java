package javasabr.leetcode.encode;

import java.util.PriorityQueue;

public class Huffman {

  public static void main(String[] args) {

    char[] charArray = {'a', 'b', 'c', 'd', 'e', 'f'};
    int[] charfreq = {5, 9, 12, 13, 16, 45};

    printCode(buildTree(charArray, charfreq), "");
    System.out.println("===========================");

    char[] charArray2 = {'a', 'b', 'c', 'd', 'e', 'f', 'j', 'y', 'q'};
    int[] charfreq2 = {5, 9, 12, 13, 16, 45, 13, 32, 7};

    printCode(buildTree(charArray2, charfreq2), "");
  }

  private static Node buildTree(char[] chars, int[] freq) {

    var length = chars.length;
    var queue = new PriorityQueue<Node>(length);

    Node root = null;

    for (int i = 0; i < length; i++) {
      Node node = new Node();
      node.ch = chars[i];
      node.freq = freq[i];
      queue.add(node);
      root = node;
    }

    while (queue.size() > 1) {

      Node left = queue.remove();
      Node right = queue.remove();

      Node combined = new Node();
      combined.freq = left.freq + right.freq;
      combined.ch = '-';
      combined.left = left;
      combined.right = right;

      root = combined;

      queue.add(combined);
    }

    return root;
  }

  public static void printCode(Node node, String prefix) {

    if (node.left == null && node.right == null && Character.isLetter(node.ch)) {
      // c is the character in the node
      System.out.println(node.ch + ":" + prefix);
      return;
    }

    printCode(node.left, prefix + "0");
    printCode(node.right, prefix + "1");
  }

  static class Node implements Comparable<Node> {

    int freq;
    char ch;

    Node left;
    Node right;

    @Override
    public int compareTo(Node another) {
      return freq - another.freq;
    }

    @Override
    public String toString() {
      return "Node{" + "ch=" + ch + ", freq=" + freq + '}';
    }
  }
}
