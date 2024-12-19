package javasabr.leetcode.jmh;

import javasabr.leetcode.array.ThreeSum;
import javasabr.leetcode.string.GenerateParentheses;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class GenerateParenthesesBenchmark {

  //@Benchmark
  public void baseline(Blackhole bh) {
    var result = GenerateParentheses.baseline(8);
    bh.consume(result);
  }

  @Benchmark
  public void optimize3(Blackhole bh) {
    var result = GenerateParentheses.optimize3(8);
    bh.consume(result);
  }
}
