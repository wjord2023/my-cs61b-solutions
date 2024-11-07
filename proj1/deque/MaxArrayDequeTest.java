package deque;

import static org.junit.Assert.*;

import java.util.Comparator;
import org.junit.Test;

public class MaxArrayDequeTest {

  @Test
  public void testMaxWithCustomComparator() {
    MaxArrayDeque<String> deque = new MaxArrayDeque<>((s1, s2) -> s1.length() - s2.length());
    deque.addLast("a");
    deque.addLast("ab");
    deque.addLast("abc");
    deque.addLast("c");
    assertEquals("abc", deque.max());
  }

  static class TestObject {
    private int value;

    public TestObject(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  // 定义一个比较器，按 TestObject 的 value 属性进行比较
  static class TestObjectComparator implements Comparator<TestObject> {
    @Override
    public int compare(TestObject o1, TestObject o2) {
      return Integer.compare(o1.getValue(), o2.getValue());
    }
  }

  @Test
  public void testMaxWithEmptyDeque() {
    MaxArrayDeque<TestObject> deque = new MaxArrayDeque<>(new TestObjectComparator());
    assertNull(deque.max());
  }

  @Test
  public void testMaxWithSingleElement() {
    MaxArrayDeque<TestObject> deque = new MaxArrayDeque<>(new TestObjectComparator());
    deque.addLast(new TestObject(10));
    assertEquals(10, deque.max().getValue());
  }

  @Test
  public void testMaxWithMultipleElements() {
    MaxArrayDeque<TestObject> deque = new MaxArrayDeque<>(new TestObjectComparator());
    deque.addLast(new TestObject(5));
    deque.addLast(new TestObject(15));
    deque.addLast(new TestObject(10));

    // 使用 TestObject(15) 作为最大值
    assertEquals(15, deque.max(new TestObjectComparator()).getValue());
  }

  @Test
  public void testMaxWithDifferentComparator() {
    MaxArrayDeque<TestObject> deque = new MaxArrayDeque<>(new TestObjectComparator());
    deque.addLast(new TestObject(20));
    deque.addLast(new TestObject(5));
    deque.addLast(new TestObject(30));

    // 自定义最大值测试，预期为 TestObject(30)
    TestObject max = deque.max();
    assertEquals(30, max.getValue());
  }

  @Test
  public void maxWithoutComparatorTest() {
    MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntComparator());

    for (int i = 0; i < 5; i++) {
      mad.addLast(i);
    }

    assertEquals((Integer) 4, mad.max());
  }

  @Test
  public void maxWithComparatorTest() {
    MaxArrayDeque<String> mad = new MaxArrayDeque<>(new StringComparator());

    mad.addLast("Java is good!");
    mad.addLast("java is good");

    assertEquals("java is good", mad.max());
    assertEquals("Java is good!", mad.max(new StringLengthComparator()));
  }

  private static class IntComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
      return i1 - i2;
    }
  }

  private static class StringComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
      int l1 = s1.length();
      int l2 = s2.length();

      for (int i = 0; i < Math.min(l1, l2); i++) {
        int s1Char = s1.charAt(i);
        int s2Char = s2.charAt(i);

        if (s1Char != s2Char) {
          return s1Char - s2Char;
        }
      }

      if (l1 != l2) {
        return l1 - l2;
      }
      return 0;
    }
  }

  private static class StringLengthComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
      return s1.length() - s2.length();
    }
  }
}
