# Homework 20 - Tree and Recursion

## **Answer 1**

```java
public static <T> int size(Tree<T> t) {
        Sequence<Tree<T>> children = t.newSequenceOfTree();
        T root = t.disassemble(children);

        int totalSize = 1; // Count the root node
        for (int i = 0; i < children.length(); i++) {
                Tree<T> subtree = children.remove(0);
                totalSize += size(subtree);
                children.add(children.length(), subtree);
        }

        t.assemble(root, children);
        return totalSize;
}
```

---

## **Answer 2**

```java
public static <T> int sizeIterative(Tree<T> t) {
        int totalSize = 0;
        Stack<Tree<T>> stack = new Stack<>();
        stack.push(t);

        while (!stack.isEmpty()) {
                Tree<T> current = stack.pop();
                totalSize++;

                Sequence<Tree<T>> children = current.newSequenceOfTree();
                T root = current.disassemble(children);

                for (int i = 0; i < children.length(); i++) {
                Tree<T> subtree = children.remove(0);
                stack.push(subtree);
                children.add(children.length(), subtree);
                }

                current.assemble(root, children);
        }

        return totalSize;
}
```

---

## **Answer 3**

```java
public static <T> int height(Tree<T> t) {
        if (t.numberOfSubtrees() == 0) {
                return 0;
        }

        Sequence<Tree<T>> children = t.newSequenceOfTree();
        T root = t.disassemble(children);

        int maxHeight = 0;
        for (int i = 0; i < children.length(); i++) {
                Tree<T> subtree = children.remove(0);
                maxHeight = Math.max(maxHeight, height(subtree));
                children.add(children.length(), subtree);
        }

        t.assemble(root, children);
        return 1 + maxHeight;
}
```

---

## **Answer 4**

```java
// Recursive implementation of max method
public static int max(Tree<Integer> t) {
        Sequence<Tree<Integer>> children = t.newSequenceOfTree();
        int rootValue = t.disassemble(children);

        int maxValue = rootValue;
        for (int i = 0; i < children.length(); i++) {
                Tree<Integer> subtree = children.remove(0);
                maxValue = Math.max(maxValue, max(subtree));
                children.add(children.length(), subtree);
        }

        t.assemble(rootValue, children);
        return maxValue;
}
```
