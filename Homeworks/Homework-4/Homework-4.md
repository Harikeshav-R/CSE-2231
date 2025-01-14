# Homework 4

### **Answer 1:**

The claim that the average of two Java `int` values `j` and `k` is always representable as an `int`, regardless of the lower and upper bounds of the `int` type, can be justified as follows:

- The average is defined as `average = (j + k)/2`. For two integers `j` and `k`, the sum `j + k` might exceed the range of representable `int` values if added directly, leading to overflow.

- The average formula can be rearranged such that computing `j + k` directly can be avoided, and thus prevent overflow in intermediate steps.

- Java’s integer division truncates toward zero. This means the result of `(j + k)/2` will always be an integer, and no rounding issues can push it outside the range of representable `int` values.

---

### **Answer 2:**

```java
public static int average(int j, int k) {
    return (j / 2) + (k / 2) + ((j % 2 + k % 2) / 2);
}
```
