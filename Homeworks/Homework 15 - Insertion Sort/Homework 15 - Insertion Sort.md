# Homework 15 - Insertion Sort

## **Answer 1**

```java
/**
* Inserts the given {@code T} in the {@code Queue<T>} sorted according to
* the given {@code Comparator<T>} and maintains the {@code Queue<T>}
* sorted.
*
* @param <T>
*            type of {@code Queue} entries
* @param q
*            the {@code Queue} to insert into
* @param x
*            the {@code T} to insert
* @param order
*            the {@code Comparator} defining the order for {@code T}
* @updates q
* @requires <pre>
* IS_TOTAL_PREORDER([relation computed by order.compare method])  and
* IS_SORTED(q, [relation computed by order.compare method])
* </pre>
* @ensures <pre>
* perms(q, #q * <x>)  and
* IS_SORTED(q, [relation computed by order.compare method])
* </pre>
*/
private static <T> void insertInOrder(Queue<T> q, T x, Comparator<T> order) {
    Queue<T> temp = q.newInstance();

    while (q.size() != 0 && order.compare(q.front(), x) < 0){
        temp.enqueue(q.dequeue());
    }

    temp.enqueue(x);

    while (q.size() != 0){
        temp.enqueue(q.dequeue());
    }

    q.transferFrom(temp);
}
```

---

## **Answer 2**

```java
/**
* Sorts {@code this} according to the ordering provided by the
* {@code compare} method from {@code order}.
*
* @param order
*            ordering by which to sort
* @updates this
* @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
* @ensures <pre>
* perms(this, #this)  and
* IS_SORTED(this, [relation computed by order.compare method])
* </pre>
*/
public void sort(Comparator<T> order) {
    Queue<T> temp = this.newInstance();

    while (this.size() != 0){
        insertInOrder(temp, this.dequeue(), order);
    }

    this.transferFrom(temp);
}
```

---

## **Answer 3**

| Statement                                                               | Variable Values                          |
| ----------------------------------------------------------------------- | ---------------------------------------- |
| `SortingMachine<Integer> sm = new SortingMachine1L<>(new IntegerGE());` | `sm = (true, >=, {})`                    |
| `sm.add(0);`                                                            | `sm = (true, >=, {0})`                   |
| `sm.add(2);`                                                            | `sm = (true, >=, {0, 2})`                |
| `sm.add(-1);`                                                           | `sm = (true, >=, {0, 2, -1})`            |
| `sm.changeToExtractionMode();`                                          | `sm = (false, >=, {0, 2, -1})`           |
| `int i = sm.removeFirst();`                                             | `sm = (false, >=, {0, -1})` <br> `i = 2` |
| `sm.clear();`                                                           | `sm = ()` <br> `i = 2`                   |
