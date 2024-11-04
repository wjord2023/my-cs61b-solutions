package randomizedtest;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

/** Created by hug. */
public class TestBuggyAList {
  // YOUR TESTS HERE
  @Test
  public void testThreeAddThreeRemove() {

    AListNoResizing<Integer> a = new AListNoResizing<>();
    BuggyAList<Integer> b = new BuggyAList<>();

    a.addLast(5);
    a.addLast(10);
    a.addLast(15);

    b.addLast(5);
    b.addLast(10);
    b.addLast(15);

    assertEquals(a.size(), b.size());

    assertEquals(a.removeLast(), b.removeLast());
    assertEquals(a.removeLast(), b.removeLast());
    assertEquals(a.removeLast(), b.removeLast());
  }

  @Test
  public void randomizedTest() {
    AListNoResizing<Integer> L = new AListNoResizing<>();
    BuggyAList<Integer> B = new BuggyAList<>();

    int N = 5000;
    for (int i = 0; i < N; i += 1) {
      int operationNumber = StdRandom.uniform(0, 3);
      if (operationNumber == 0) {
        // addLast
        int randVal = StdRandom.uniform(0, 100);
        L.addLast(randVal);
        B.addLast(randVal);
        System.out.println("addLast(" + randVal + ")");
      } else if (operationNumber == 1) {
        // size
        int size1 = L.size();
        int size2 = B.size();
        assertEquals(size1, size2);
      } else if (operationNumber == 2 && L.size() > 0) {
        int removed1 = L.removeLast();
        int removed2 = B.removeLast();
        assertEquals(removed1, removed2);
        System.out.println("removeLast(" + removed1 + ")");
      } else if (operationNumber == 3 && L.size() > 0) {
        int last1 = L.getLast();
        int last2 = B.getLast();
        assertEquals(last1, last2);
        System.out.println("removeLast(" + last1 + ")");
      }
    }
  }
}
