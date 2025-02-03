# Homework 12 - BinaryTree and Recursion I

## **Answer 1**

```java
private static <T> int recursiveSize(BinaryTree<T> tree) {
    int treeSize;

    if (tree.height() == 0) {
        treeSize = 0;
    } else {
        BinaryTree<T> left = tree.newInstance(), right = tree.newInstance();
        T root = tree.disassemble(left, right);

        treeSize = 1 + size(left) + size(right);
        tree.assemble(root, left, right);
    }

    return treeSize;

}
```

---

## **Answer 2**

```java
public static <T> int iterativeSize(BinaryTree<T> tree) {
    int treeSize = 0;
    Stack<BinaryTree<T>> stack = new Stack1L<>();
    stack.push(tree);

    while (stack.length() != 0) {
        BinaryTree<T> current = stack.pop();

        if (current.height() > 0) {
            BinaryTree<T> left = current.newInstance(), right = current.newInstance();
            T root = current.disassemble(left, right);

            treeSize++;
            stack.push(left);
            stack.push(right);

            current.assemble(root, left, right);
        }
    }

    return treeSize;
}
```
