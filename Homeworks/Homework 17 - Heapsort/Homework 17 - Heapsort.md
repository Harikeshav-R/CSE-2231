# Homework 17 - Heapsort

## **Answer 1**

![Answer 1A](image1.png "Answer 1A")
![Answer 1B](image2.png "Answer 1B")
![Answer 1C](image3.png "Answer 1C")
![Answer 1D](image4.png "Answer 1D")
![Answer 1E](image5.png "Answer 1E")

---

## **Answer 2**

```java
private static boolean satisfiesHeapOrdering(BinaryTree<Integer> t) {
    if (t.size() == 0) {
        return true;
    }

    BinaryTree<Integer> left = new BinaryTree<>();
    BinaryTree<Integer> right = new BinaryTree<>();
    Integer root = t.disassemble(left, right);

    boolean isHeap = true;

    if (!left.isEmpty()) {
        isHeap = root <= left.root() && satisfiesHeapOrdering(left);
    }

    if (isHeap && !right.isEmpty()) {
        isHeap = root <= right.root() && satisfiesHeapOrdering(right);
    }

    t.assemble(root, left, right);

    return isHeap;
}
```
