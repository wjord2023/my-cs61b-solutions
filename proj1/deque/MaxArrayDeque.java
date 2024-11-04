package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
  private Comparator<T> c;

  public MaxArrayDeque(Comparator<T> c) {
    super();
    this.c = c;
  }

  public T max() {
    return max(this.c);
  }

  public T max(Comparator<T> c) {
    Iterator<T> iterator = this.iterator();
    if (iterator.hasNext()) {
      T maxVal = iterator.next();
      while (iterator.hasNext()) {
        if (c.compare(maxVal, iterator.next()) > 0) {
          maxVal = iterator.next();
        }
      }
      return maxVal;
    } else {
      return null;
    }
  }
}
