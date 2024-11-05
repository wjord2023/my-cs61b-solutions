package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
  private static class LinkedNode<T> {
    public T item;
    public LinkedNode<T> pre;
    public LinkedNode<T> next;

    public LinkedNode(T item, LinkedNode<T> pre, LinkedNode<T> next) {
      this.item = item;
      this.pre = pre;
      this.next = next;
    }
  }

  private LinkedNode<T> sentinel;
  private int size;

  private class LinkedListDequeIterator implements Iterator<T> {
    private int currentIndex = 0;
    private LinkedNode<T> currentNode = sentinel;

    @Override
    public boolean hasNext() {
      return currentIndex < size;
    }

    @Override
    public T next() {
      currentIndex += 1;
      currentNode = currentNode.next;
      return currentNode.item;
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new LinkedListDequeIterator();
  }

  public LinkedListDeque() {
    this.sentinel = new LinkedNode<>(null, null, null);
    this.sentinel.pre = this.sentinel;
    this.sentinel.next = this.sentinel;

    this.size = 0;
  }

  @Override
  public void addFirst(T item) {
    LinkedNode<T> addNode = new LinkedNode<>(item, sentinel, sentinel.next);
    sentinel.next.pre = addNode;
    sentinel.next = addNode;

    size += 1;
  }

  @Override
  public void addLast(T item) {
    LinkedNode<T> addNode = new LinkedNode<>(item, sentinel.pre, sentinel);
    sentinel.pre.next = addNode;
    sentinel.pre = addNode;

    size += 1;
  }

  @Override
  public T removeFirst() {
    if (isEmpty()) {
      return null;
    }
    LinkedNode<T> removeNode = sentinel.next;
    sentinel.next = removeNode.next;
    removeNode.next.pre = sentinel;

    size -= 1;
    return removeNode.item;
  }

  @Override
  public T removeLast() {
    if (isEmpty()) {
      return null;
    }
    LinkedNode<T> removeNode = sentinel.pre;
    sentinel.pre = removeNode.pre;
    removeNode.pre.next = sentinel;

    size -= 1;
    return removeNode.item;
  }

  @Override
  public T get(int index) {
    for (T item : this) {
      if (index == 0) {
        return item;
      }
      index -= 1;
    }
    return null;
  }

  private T getRecursive(int index, LinkedNode<T> first) {
    if (first == sentinel || index < 0) {
      return null;
    }
    if (index == 0) {
      return first.item;
    }

    return getRecursive(index - 1, first.next);
  }

  public T getRecursive(int index) {
    return getRecursive(index, sentinel.next);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void printDeque() {
    for (T item : this) {
      System.out.print(item + " ");
    }
    System.out.println();
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

    for (int i = 0; i < this.size(); i++) {
      if (this.get(i).equals(other.get(i))) {
        return false;
      }
    }

    return true;
  }
}
