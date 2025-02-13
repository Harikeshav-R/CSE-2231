# Homework 16 - Quicksort

## **Answer 1**

```java
public static <T> void partition(Queue<T> q, T partitioner,
                                     Queue<T> front, Queue<T> back, Comparator<T> order) {
    front.clear();
    back.clear();

    while (q.length() > 0) {
        T element = q.dequeue();
        if (order.compare(element, partitioner) <= 0) {
            front.add(element);
        } else {
            back.add(element);
        }
    }
}
```

## **Answer 2**

```java
public void sort(Comparator<T> order) {
    if (this.length() > 1) {
        T partitioner = this.dequeue();
        Queue<T> front = this.newInstance();
        Queue<T> back = this.newInstance();

        partition(this, partitioner, front, back, order);

        front.sort(order);
        back.sort(order);

        this.append(front);
        this.enqueue(partitioner);
        this.append(back);
    }
}
```
