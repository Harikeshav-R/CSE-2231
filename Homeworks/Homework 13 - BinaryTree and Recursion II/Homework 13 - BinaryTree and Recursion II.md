# Homework 13 - BinaryTree and Recursion II

## **Answer 1**

```java
/**
* Returns the {@code String} prefix representation of the given
* {@code BinaryTree<T>}.
*
* @param <T>
*            the type of the {@code BinaryTree} node labels
* @param t
*            the {@code BinaryTree} to convert to a {@code String}
* @return the prefix representation of {@code t}
* @ensures treeToString = [the String prefix representation of t]
*/
public static <T> String treeToString(BinaryTree<T> t) {
    StringBuilder str = new StringBuilder();
    if (t.size() == 0) {
        str.append("()");
    } else {
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T root = t.disassemble(left, right);
        str.append(root);
        str.append("(");
        str.append(treeToString(left));
        str.append(treeToString(right));
        str.append(")");
        t.assemble(root, left, right);
    }
    return str.toString();
}
```

---

## **Answer 2**

```java
/**
* Returns a copy of the the given {@code BinaryTree}.
*
* @param t
*            the {@code BinaryTree} to copy
* @return a copy of the given {@code BinaryTree}
* @ensures copy = t
*/
public static BinaryTree<Integer> copy(BinaryTree<Integer> t) {
    if (t.height() == 0) {
        return t.newInstance();
    }

    BinaryTree<Integer> left = t.newInstance(), right = t.newInstance();
    int root = t.disassemble(left, right);

    BinaryTree<Integer> copyTree = t.newInstance();
    copyTree.assemble(root, copy(left), copy(right));

    t.assemble(root, left, right);

    return copyTree;
}
```
