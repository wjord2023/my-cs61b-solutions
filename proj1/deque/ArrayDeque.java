package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
  private T[] items;
  private int firstArraySize;
  private int lastArraySize;

  public ArrayDeque() {
    this.items = (T[]) new Object[8];
    this.firstArraySize = 0;
    this.lastArraySize = 0;
  }

  private void resize(int capacity) {
    T[] newItems = (T[]) new Object[capacity];
    System.arraycopy(items, 0, newItems, 0, lastArraySize);
    System.arraycopy(items, lastArraySize, newItems, capacity - lastArraySize, firstArraySize);

    this.items = newItems;
  }

  private boolean isFull() {
    return firstArraySize + lastArraySize == items.length;
  }

  private int firstIndex() {
    return items.length - firstArraySize;
  }

  private int lastIndex() {
    return lastArraySize - 1;
  }

  @Override
  public void addFirst(T item) {
    if (isFull()) {
      resize(items.length * 2);
    }
    firstArraySize += 1;
    items[firstIndex()] = item;
  }

  @Override
  public void addLast(T item) {
    if (isFull()) {
      resize(items.length * 2);
    }
    lastArraySize += 1;
    items[lastIndex()] = item;
  }

  @Override
  public boolean isEmpty() {
    return lastArraySize == 0 && firstArraySize == 0;
  }

  @Override
  public int size() {
    return firstArraySize + lastArraySize;
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
    T removed = items[firstIndex()];
    firstArraySize -= 1;
    return removed;
  }

  @Override
  public T removeLast() {
    T removed = items[lastIndex()];
    lastArraySize -= 1;
    return removed;
  }

  @Override
  public T get(int index) {
    if (index <= firstArraySize) {
      return items[items.length - index];
    } else {
      return items[index - firstArraySize - 1];
    }
  }

  private class ArrayDequeIterator<T> implements Iterator<T> {
    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < firstArraySize + lastArraySize;
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
    if (!(o instanceof ArrayDeque)) {
      return false;
    }

    ArrayDeque<T> other = (ArrayDeque<T>) o;
    return other.items.equals(this.items);
  }
}
