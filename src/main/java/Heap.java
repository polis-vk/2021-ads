public class Heap {
  public enum HeapType {
    MIN_ORDERED,
    MAX_ORDERED
  }

  private int[] data;
  private int size;
  private HeapType heapType;

  public Heap(int maxSize, HeapType ht) {
    this.data = new int[maxSize + 1];
    this.heapType = ht;
  }
  {
    size = 0;
  }

  public void insert(int elem) {
    if (size == data.length) {
      throw new RuntimeException("Heap is overflowed");
    }
    data[++size] = elem;
    swim(size);
  }

  public int extract() {
    if (isEmpty()) {
      throw new RuntimeException("Heap is empty");
    }
    int max = data[1];
    swap(data, 1, size--);
    sink(data, 1, data.length, heapType);
    return max;
  }

  public int peek() {
    return data[1];
  }

  public void swim(int k) {
    switch (heapType) {
      case MIN_ORDERED: swimMinOrdered(k); break;
      case MAX_ORDERED: swimMaxOrdered(k); break;
    }
  }

  private void swimMinOrdered(int k) {
    while (k > 1 && data[k] < data[k/2]) {
      swap(data, k, k/2);
      k /= 2;
    }
  }

  private void swimMaxOrdered(int k) {
    while (k > 1 && data[k] > data[k/2]) {
      swap(data, k, k/2);
      k /= 2;
    }
  }

  public static void sink(int[] a, int start, int end, HeapType ht) {
    switch (ht) {
      case MIN_ORDERED: sinkMinOrdered(a, start, end); break;
      case MAX_ORDERED: sinkMaxOrdered(a, start, end); break;
    }
  }

  private static void sinkMinOrdered(int[] a, int start, int end) {
    int k = start;
    while (2*k < end) {
      int j = 2*k;
      if (j < end - 1 && a[j] > a[j + 1])
        ++j;
      if (a[k] <= a[j])
        break;
      swap(a, k, j);
      k = j;
    }
  }

  private static void sinkMaxOrdered(int[] a, int start, int end) {
    int k = start;
    while (2*k < end) {
      int j = 2*k;
      if (j < end - 1 && a[j] < a[j + 1])
        ++j;
      if (a[k] >= a[j])
        break;
      swap(a, k, j);
      k = j;
    }
  }

  private static void swap(int[] a, int i, int j) {
    int tmp = a[i];
    a[i] = a[j];
    a[j] = tmp;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public static void heapSort(int[] a, int start, int end, HeapType ht) {
    int n = end;
    for (int k = n/2; k >= start; --k) {
      Heap.sink(a, k, n, ht);
    }
    while (n > start) {
      Heap.swap(a, start, --n);
      Heap.sink(a, start, n, ht);
    }
  }
}
