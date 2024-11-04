package timingtest;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;

/** Created by hug. */
public class TimeSLList {
  private static void printTimingTable(
      AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
    System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
    System.out.printf("------------------------------------------------------------\n");
    for (int i = 0; i < Ns.size(); i += 1) {
      int N = Ns.get(i);
      double time = times.get(i);
      int opCount = opCounts.get(i);
      double timePerOp = time / opCount * 1e6;
      System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
    }
  }

  public static void main(String[] args) {
    timeGetLast();
  }

  public static void timeGetLast() {
    // TODO: YOUR CODE HERE
    AList<Integer> Ns = new AList<>();
    AList<Double> times = new AList<>();
    AList<Integer> opCounts = new AList<>();

    for (int i = 1000; i <= 64000; i *= 2) {
      SLList<Integer> L = new SLList<>();
      for (int j = 0; j < i; j++) {
        L.addLast(1);
      }
      Stopwatch sw = Stopwatch.createStarted();
      for (int j = 0; j < 10000; j++) {
        L.getLast();
      }
      sw.stop();
      double timeInSeconds = sw.elapsed(TimeUnit.MILLISECONDS) / 1000.0;
      Ns.addLast(i);
      times.addLast(timeInSeconds);
      opCounts.addLast(10000);
    }
    printTimingTable(Ns, times, opCounts);
  }
}
