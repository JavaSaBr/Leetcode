package javasabr.leetcode.jmh;

import javasabr.leetcode.string.LetterPhoneNumberCombination;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class LetterPhoneNumberCombinationBenchmark {

  @Benchmark
  public void baseline(Blackhole bh) {
    var result = LetterPhoneNumberCombination.baseline("2345");
    bh.consume(result);
  }

  @Benchmark
  public void optimize1(Blackhole bh) {
    var result = LetterPhoneNumberCombination.optimize1("2345");
    bh.consume(result);
  }
}
