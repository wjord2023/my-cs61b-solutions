package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
  private T[] items;
  private int firstIndex;
  private int lastIndex;
  private int size;
  private boolean inited;

  public ArrayDeque() {
    this.items = (T[]) new Object[8];
    this.firstIndex = 0;
    this.lastIndex = 0;
    this.size = 0;
    this.inited = false;
  }

  private void resize(int capacity) {
    T[] newItems = (T[]) new Object[capacity];

    for (int i = 0; i < size; i++) {
      newItems[i] = items[(firstIndex + i) % items.length];
    }

    items = newItems;
    firstIndex = 0;
    lastIndex = size - 1;
  }

  private boolean isFull() {
    return size == items.length;
  }

  private void decreaseFirstIndex() {
    if (firstIndex == 0) {
      firstIndex = items.length - 1;
    } else {
      firstIndex -= 1;
    }
  }

  private void decreaseLastIndex() {
    if (lastIndex == 0) {
      lastIndex = items.length - 1;
    } else {
      lastIndex -= 1;
    }
  }

  private void increaseLastIndex() {
    if (lastIndex == items.length - 1) {
      lastIndex = 0;
    } else {
      lastIndex += 1;
    }
  }

  private void increaseFirstIndex() {
    if (firstIndex == items.length - 1) {
      firstIndex = 0;
    } else {
      firstIndex += 1;
    }
  }

  @Override
  public void addFirst(T item) {
    if (inited == false) {
      items[0] = item;
      inited = true;
      size += 1;
      return;
    }
    if (isFull()) {
      resize(items.length * 2);
    }
    decreaseFirstIndex();
    items[firstIndex] = item;
    size += 1;
  }

  @Override
  public void addLast(T item) {
    if (inited == false) {
      items[0] = item;
      inited = true;
      size += 1;
      return;
    }
    if (isFull()) {
      resize(items.length * 2);
    }
    increaseLastIndex();
    items[lastIndex] = item;

    size += 1;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void printDeque() {
    for (T item : items) {
      System.out.print(item + " ");
    }
    System.out.println();
  }

  @Override
  public T removeFirst() {
    if (size < items.length / 4) {
      resize(items.length / 2);
    }
    if (isEmpty()) {
      return null;
    }
    T removed = items[firstIndex];
    items[firstIndex] = null;
    increaseFirstIndex();

    size -= 1;
    return removed;
  }

  @Override
  public T removeLast() {
    if (size < items.length / 4) {
      resize(items.length / 2);
    }
    if (isEmpty()) {
      return null;
    }
    T removed = items[lastIndex];
    items[lastIndex] = null;
    decreaseLastIndex();

    size -= 1;
    return removed;
  }

  @Override
  public T get(int index) {
    if (index < size) {
      return items[(firstIndex + index) % items.length];
    } else {
      return null;
    }
  }

  private class ArrayDequeIterator<T> implements Iterator<T> {
    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < size;
    }

    @Override
    public T next() {
      T returnItem = (T) get(index);
      index++;
      return returnItem;
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new ArrayDequeIterator();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Deque)) {
      return false;
    }

    Deque<T> other = (Deque<T>) o;
    if (this.size() != other.size()) {
      return false;
    }

    Iterator<T> thisIter = this.iterator();
    Iterator<T> otherIter = other.iterator();
    while (thisIter.hasNext()) {
      if (!thisIter.next().equals(otherIter.next())) {
        return false;
      }
    }
    return true;
  }
}
