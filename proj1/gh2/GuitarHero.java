package gh2;

import deque.ArrayDeque;
import deque.Deque;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
  private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
  private static Deque<GuitarString> strings = new ArrayDeque<GuitarString>();

  public static void main(String[] args) {
    int N = keyboard.length();
    for (int i = 0; i < N; i++) {
      double frequency = 440 * Math.pow(2, (i - 24) / 12.0);
      GuitarString string = new GuitarString(frequency);
      strings.addLast(string);
    }
    while (true) {
      if (StdDraw.hasNextKeyTyped()) {
        char key = StdDraw.nextKeyTyped();
        for (int i = 0; i < N; i++) {
          if (key == keyboard.charAt(i)) {
            strings.get(i).pluck();
          }
        }
      }

      double sample = 0;
      for (int i = 0; i < N; i++) {
        sample += strings.get(i).sample();
      }

      StdAudio.play(sample);

      for (int i = 0; i < N; i++) {
        strings.get(i).tic();
      }
    }
  }
}
