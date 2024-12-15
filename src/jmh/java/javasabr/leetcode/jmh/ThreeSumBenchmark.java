package javasabr.leetcode.jmh;

import javasabr.leetcode.array.ThreeSum;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class ThreeSumBenchmark {

  public static final int[] INPUT_1 = {
      82597,
      -9243,
      62390,
      83030,
      -97960,
      -26521,
      -61011,
      83390,
      -38677,
      12333,
      75987,
      46091,
      83794,
      19355,
      -71037,
      -6242,
      -28801,
      324,
      1202,
      -90885,
      -2989,
      -95597,
      -34333,
      35528,
      5680,
      89093,
      -90606,
      50360,
      -29393,
      -27012,
      53313,
      65213,
      99818,
      -82405,
      -41661,
      -3333,
      -51952,
      72135,
      -1523,
      26377,
      74685,
      96992,
      92263,
      15929,
      5467,
      -99555,
      -43348,
      -41689,
      -60383,
      -3990,
      32165,
      65265,
      -72973,
      -58372,
      12741,
      -48568,
      -46596,
      72419,
      -1859,
      34153,
      62937,
      81310,
      -61823,
      -96770,
      -54944,
      8845,
      -91184,
      24208,
      -29078,
      31495,
      65258,
      14198,
      85395,
      70506,
      -40908,
      56740,
      -12228,
      -40072,
      32429,
      93001,
      68445,
      -73927,
      25731,
      -91859,
      -24150,
      10093,
      -60271,
      -81683,
      -18126,
      51055,
      48189,
      -6468,
      25057,
      81194,
      -58628,
      74042,
      66158,
      -14452,
      -49851,
      -43667,
      11092,
      39189,
  };

  //@Benchmark
  public void optimize6(Blackhole bh) {
    var result = ThreeSum.optimize6(INPUT_1);
    bh.consume(result);
  }

  //@Benchmark
  public void optimize1(Blackhole bh) {
    var result = ThreeSum.optimize1(INPUT_1);
    bh.consume(result);
  }
}
