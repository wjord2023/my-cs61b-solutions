package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
  private final Comparator<T> comparator;

  public MaxArrayDeque(Comparator<T> c) {
    super();
    this.comparator = c;
  }

  public T max() {
    return max(this.comparator);
  }

  public T max(Comparator<T> c) {
    if (isEmpty()) {
      return null;
    }
    T maxItem = get(0);
    for (int i = 1; i < size(); i++) {
        T current = get(i);
        if (c.compare(current, maxItem) > 0) {
            maxItem = current;
        }
    }
    return maxItem;
  }
}
