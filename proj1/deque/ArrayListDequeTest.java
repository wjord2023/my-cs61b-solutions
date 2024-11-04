package deque;

import static org.junit.Assert.*;

import java.util.Random;
import org.junit.Test;

public class ArrayListDequeTest {
  @Test
  public void randomizedTest() {
    LinkedListDeque<Integer> lld = new LinkedListDeque<Integer>();
    ArrayDeque<Integer> ad = new ArrayDeque<Integer>();

    Random random = new Random();
    int N = 100000;

    for (int i = 0; i < N; i += 1) {
      int operationNumber = random.nextInt(6);
      if (operationNumber == 0) {
        int randVal = random.nextInt(100);
        ad.addFirst(randVal);
        lld.addFirst(randVal);
      } else if (operationNumber == 1) {
        int randVal = random.nextInt(100);
        ad.addLast(randVal);
        lld.addLast(randVal);
      } else if (operationNumber == 2) {
        if (lld.isEmpty()) {
          assertTrue(ad.isEmpty());
        } else {
          int actual = ad.removeFirst();
          int expected = lld.removeFirst();
          assertEquals(expected, actual);
        }
      } else if (operationNumber == 3) {
        if (lld.isEmpty()) {
          assertTrue(ad.isEmpty());
        } else {
          int actual = ad.removeLast();
          int expected = lld.removeLast();
          assertEquals(expected, actual);
        }
      } else if (operationNumber == 4) {
        int size = lld.size();
        assertEquals(size, ad.size());
      } else {
        if (lld.isEmpty()) {
          assertTrue(ad.isEmpty());
        } else {
          int index = random.nextInt(lld.size());
          int actual = ad.get(index);
          int expected = lld.get(index);
          assertEquals(expected, actual);
        }
      }
    }
  }
}
