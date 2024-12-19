package javasabr.leetcode.string;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateParentheses {

  private static String[][] MATRIX;
  private static List<String>[] SYMMETRIC_CACHE = new List[9];
  private static List<String>[] INJECT_CACHE = new List[9];

  static {
    var matrix = new String[9][];
    matrix[1] = new String[]{"()"};
    matrix[2] = new String[]{"(())", "()()"};
    matrix[3] = new String[]{"((()))", "()()()", "()(())", "(())()"};

    MATRIX = matrix;

    SYMMETRIC_CACHE[1] = List.of("()");
    SYMMETRIC_CACHE[2] = List.of("(())", "()()");
    SYMMETRIC_CACHE[3] = List.of("()()()", "(()())", "((()))");
  }

  private static List<String> MATRIX_CALCULATION = new ArrayList<>();

  public static void main(String[] args) {

    //System.out.println(baseline(2));
    //System.out.println(baseline(3));
    //["((()))","(()())","(())()","()(())","()()()"]
    //[()()(), (()()), ()(()), ((())), (())()]
    //System.out.println(baseline(4));
    System.out.println(optimize1(4));
    System.out.println(optimize2(4));
    // ["(((())))","((()()))","((())())","((()))()","(()(()))","(()()())","(()())()","(())(())","(())()()","()((()))
    // ","()(()())","()(())()","()()(())","()()()()"]
    // [()()()(), (()()()), ((()())), (((()))), ((()))(), ((())()), ()((())), (()(()))]
    // [()()()(), (()()()), ((()())), (((()))), (())(()), ((()))(), ((())()), (())(()), ()((())), (()(()))]
    // (())(())

    //[()()()(), (()())(), (()(())), ()()(()), (((()))), (())()(), (())(()), ()((())), ()(())(),
    // ()(()()), (()()()), ((()())), ((()))(), ((())())]

    // [()()()(), (()())(), (()(())), ()()(()), (((()))), (())()(), ()((())), ()(())(), ()(()()), (()()()), ((()())),
    // ((()))(), ((())())]
    // [()()()(), (()())(), (()(())), ()()(()), (((()))), (())()(), (())(()), ()((())), ()(())(), ()(()()), (()()()),
    // ((()())), ((()))(), ((())())]

    //System.out.println(baseline(5));
    //System.out.println(optimize1(5));
    //System.out.println(optimize2(5));
    //System.out.println(baseline(8));
    //System.out.println(optimize1(8));
    System.out.println(optimize2(8));
    // ["((((()))))","(((()())))","(((())()))","(((()))())","(((())))()","((()(())))","((()()()))","((()())())",
    // "((()()))()","((())(()))","((())()())","((())())()","((()))(())","((()))()()","(()((())))","(()(()()))","(()(())())",
    // "(()(()))()","(()()(()))","(()()()())","(()()())()","(()())(())","(()())()()","(())((()))","(())(()())","(())(())()",
    // "(())()(())","(())()()()","()(((())))","()((()()))","()((())())","()((()))()","()(()(()))","()(()()())","()(()())()",
    // "()(())(())","()(())()()","()()((()))","()()(()())","()()(())()","()()()(())","()()()()()"]


    // [()(())()(), (((()))()), ((()(()))), ((()))(()), ()()(()()), ()(()()()), (()(()))(), (()(()())),
    // ()((()())), ()((()))(), (()()()()), (((()()))), ()()()()(), ((()())()), (())()()(), (()(())()),
    // ()(()())(), ()(()(())), (((())))(), (((())())), (()())(()), ()(((()))), (()((()))), (())(())(),
    // (())((())), ()(())(()), ()()(())(), ()()((())), ((()))()(), ((())()()), ((((())))), ((()()())),
    // (()()())(), (()()(())), ()((())()), (())()(()), ((()()))(), ((())())(), ()()()(()), ((())(())), (())(()()), (()())()()]



    //((()))(()) (()())(()) (())((())) (())(()())
  }

  public static List<String> optimize3(int n) {

    var result = new HashSet<String>(n * n * n, 0.75F);
    var builder = new StringBuilder();

    List<String>[] cache = new List[9];

    generateN4(n, builder, cache, result);

    return List.copyOf(result);
  }

  private static Collection<String> generateN4(int n, StringBuilder builder, List<String>[] cache) {

    if (n < 1) {
      return Set.of();
    }

    List<String> cached = cache[n];
    if (cached != null) {
      return cached;
    }

    List<String> symmetric = symmetricGen2(n, builder);

    if (n < 3) {
      cache[n] = symmetric;
      return symmetric;
    }

    // inject recur flow
    var uniqSet = new HashSet<String>(n * n * n, 0.75F);
    var subResult = generateN4(n - 1, builder, cache);

    for (String variant : subResult) {

      // left inject
      builder.append('(').append(')');
      builder.append(variant);

      uniqSet.add(builder.toString());
      builder.delete(0, builder.length());

      // right inject
      builder.append(variant);
      builder.append('(').append(')');

      uniqSet.add(builder.toString());
      builder.delete(0, builder.length());

      // middle inject
      for (int j = 0; j < variant.length(); j++) {
        char ch = variant.charAt(j);
        if (ch == '(') {
          builder.append(variant, 0, j + 1);
          builder.append('(').append(')');
          builder.append(variant, j + 1, variant.length());
          uniqSet.add(builder.toString());
          builder.delete(0, builder.length());
        }
      }
    }

    cache[n] = List.copyOf(uniqSet);
    return cache[n];
  }

  private static List<String> symmetricGen2(int n, StringBuilder builder) {

    var result = new ArrayList<String>(n * 2);
    for (int level = 1; level <= n; level++) {

      int opened = 0;

      for (int pair = 0; pair < n; pair++) {
        opened++;
        builder.append('(');
        if (opened == level) {
          builder.append(')');
          opened--;
        }
      }

      while (opened > 0) {
        builder.append(')');
        opened--;
      }

      result.add(builder.toString());
      builder.delete(0, builder.length());
    }

    return result;
  }

  public static List<String> optimize2(int n) {

    var result = new HashSet<String>(n * n * n, 0.75F);
    var builder = new StringBuilder();

    generateN3(n, builder, result);

    return List.copyOf(result);
  }

  private static void generateN3(int n, StringBuilder builder, Collection<String> result) {

    if (n < 1) {
      return;
    }

    symmetricGen(n, builder, result);

    if (n < 3) {
      return;
    }

    List<String> injectCache = INJECT_CACHE[n];

    if (injectCache != null) {
      result.addAll(injectCache);
      return;
    }

    // inject recur flow
    var subResult = new HashSet<String>();

    for (int i = 1; i < n; i++) {

      int subN = n - i;

      subResult.clear();
      generateN3(subN, builder, subResult);

      for (String variant : subResult) {

        // left inject
        for (int j = 0; j < i; j++) {
          builder.append('(').append(')');
        }
        builder.append(variant);

        result.add(builder.toString());
        builder.delete(0, builder.length());

        // right inject
        builder.append(variant);
        for (int j = 0; j < i; j++) {
          builder.append('(').append(')');
        }

        result.add(builder.toString());
        builder.delete(0, builder.length());

        // middle inject
        for (int j = 0; j < variant.length(); j++) {
          char ch = variant.charAt(j);
          if (ch == '(') {
            builder.append(variant, 0, j + 1);
            for (int k = 0; k < i; k++) {
              builder.append('(').append(')');
            }
            builder.append(variant, j + 1, variant.length());
            result.add(builder.toString());
            builder.delete(0, builder.length());
          }
        }
      }
    }

    INJECT_CACHE[n] = List.copyOf(result);
  }

  private static void symmetricGen(int n, StringBuilder builder, Collection<String> result) {

    // for fast path
    var prepared = SYMMETRIC_CACHE[n];
    if (prepared != null) {
      result.addAll(prepared);
      return;
    }

    // slow path
    for (int level = 1; level <= n; level++) {

      int opened = 0;

      for (int pair = 0; pair < n; pair++) {
        opened++;
        builder.append('(');
        if (opened == level) {
          builder.append(')');
          opened--;
        }
      }

      while (opened > 0) {
        builder.append(')');
        opened--;
      }

      result.add(builder.toString());
      builder.delete(0, builder.length());
    }

    SYMMETRIC_CACHE[n] = List.copyOf(result);
  }

  public static List<String> optimize1(int n) {

    var result = new HashSet<String>();
    var builder = new StringBuilder();

    generateN2(n, builder, result);

    return List.copyOf(result);
  }

  private static void generateN2(int n, StringBuilder builder, Collection<String> result) {

    if (n < 1) {
      return;
    }

    matrixBased(n, builder, result);

    if (n < 3) {
      return;
    }

    // inject recur flow
    var subResult = new HashSet<String>();

    for (int i = 1; i <= n; i++) {

      int subN = n - i;
      if (subN < 1) {
        break;
      }

      subResult.clear();

      generateN2(subN, builder, subResult);

      for (String variant : subResult) {

        // left inject
        for (int j = 0; j < i; j++) {
          builder.append('(').append(')');
        }
        builder.append(variant);

        result.add(builder.toString());
        builder.delete(0, builder.length());

        // right inject
        builder.append(variant);
        for (int j = 0; j < i; j++) {
          builder.append('(').append(')');
        }

        result.add(builder.toString());
        builder.delete(0, builder.length());

        // middle inject
        for (int j = 0; j < variant.length(); j++) {
          char ch = variant.charAt(j);
          if (ch == '(') {
            builder.append(variant, 0, j + 1);
            for (int k = 0; k < i; k++) {
              builder.append('(').append(')');
            }
            builder.append(variant, j + 1, variant.length());
            result.add(builder.toString());
            builder.delete(0, builder.length());
          }
        }
      }
    }
  }

  private static void matrixBased(int n, StringBuilder builder, Collection<String> result) {

    // for fast path
    var prepared = MATRIX[n];
    if (prepared != null) {
      for (String variant : prepared) {
        result.add(variant);
      }
      return;
    }

    // slow path
    MATRIX_CALCULATION.clear();
    for (int level = 1; level <= n; level++) {

      int opened = 0;

      for (int pair = 0; pair < n; pair++) {
        opened++;
        builder.append('(');
        if (opened == level) {
          builder.append(')');
          opened--;
        }
      }

      while (opened > 0) {
        builder.append(')');
        opened--;
      }

      String value = builder.toString();

      MATRIX_CALCULATION.add(value);
      result.add(value);

      builder.delete(0, builder.length());
    }

    MATRIX[n] = MATRIX_CALCULATION.toArray(String[]::new);
  }

  public static List<String> baseline(int n) {

    var result = new HashSet<String>();
    var builder = new StringBuilder();

    generateN(n, builder, result);

    return List.copyOf(result);
  }

  private static void generateN(int n, StringBuilder builder, Collection<String> result) {

    if (n < 1) {
      return;
    }

    for (int level = 1; level <= n; level++) {
      simple(n, builder, level, result);
    }

    if (n < 3) {
      return;
    }

    var subResult = new HashSet<String>();

    for (int i = 1; i <= n; i++) {

      int subN = n - i;
      if (subN < 1) {
        break;
      }

      subResult.clear();

      generateN(subN, builder, subResult);

      for (String variant : subResult) {

        // left inject
        for (int j = 0; j < i; j++) {
          builder.append('(').append(')');
        }
        builder.append(variant);

        result.add(builder.toString());
        builder.delete(0, builder.length());

        // right inject
        builder.append(variant);
        for (int j = 0; j < i; j++) {
          builder.append('(').append(')');
        }

        result.add(builder.toString());
        builder.delete(0, builder.length());

        // middle inject
        for (int j = 0; j < variant.length(); j++) {
          char ch = variant.charAt(j);
          if (ch == '(') {
            builder.append(variant, 0, j + 1);
            for (int k = 0; k < i; k++) {
              builder.append('(').append(')');
            }
            builder.append(variant, j + 1, variant.length());
            result.add(builder.toString());
            builder.delete(0, builder.length());
          }
        }
      }
    }
  }

  private static void simple(int n, StringBuilder builder, int level, Collection<String> result) {

    int opened = 0;

    for (int pair = 0; pair < n; pair++) {
      opened++;
      builder.append('(');
      if (opened == level) {
        builder.append(')');
        opened--;
      }
    }

    while (opened > 0) {
      builder.append(')');
      opened--;
    }

    result.add(builder.toString());
    builder.delete(0, builder.length());
  }
}
