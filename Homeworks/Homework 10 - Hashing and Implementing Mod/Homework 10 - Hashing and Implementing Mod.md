# Homework 10 - Hashing and Implementing Mod

## **Answer 1**

```java
/**
* Computes {@code a} mod {@code b} as % should have been defined to work.
*
* @param a
*            the number being reduced
* @param b
*            the modulus
* @return the result of a mod b, which satisfies 0 <= {@code mod} < b
* @requires b > 0
* @ensures <pre>
* 0 <= mod  and  mod < b  and
* there exists k: integer (a = k * b + mod)
* </pre>
*/
public static int mod(int a, int b) {
    return ((a % b) + b) % b;
}
```

---

## **Answer 2**

| Bucket | Integers Hashed  |
| ------ | ---------------- |
| `0`    | `0`, `90`        |
| `1`    |                  |
| `2`    | `432`, `-788`    |
| `3`    |                  |
| `4`    | `54`, `84`, `-6` |
| `5`    | `-195`           |
| `6`    |                  |
| `7`    | `17`             |
| `8`    |                  |
| `9`    | `-101`           |

---

## **Answer 3**

Hashcode function: the following hashcode function separates positive and negative numbers by generating even and odd hashcodes respectively.

```java
public int hashCode(Integer x) {
    int result;

    if (x >= 0){
        result =  2 * x;
    } else {
        result = -2 * x - 1;
    }

    return result;
}
```

| `x`  | `hashCode(x)` | `hashCode(x) % 10` (Bucket) |
| ---- | ------------- | --------------------------- |
| 432  | 864           | 4                           |
| 17   | 34            | 4                           |
| 54   | 108           | 8                           |
| -788 | 1575          | 5                           |
| -101 | 201           | 1                           |
| 84   | 168           | 8                           |
| 0    | 0             | 0                           |
| -6   | 11            | 1                           |
| -195 | 389           | 9                           |
| 90   | 180           | 0                           |

| **Bucket Index** | **Elements** |
| ---------------- | ------------ |
| 0                | `{0, 90}`    |
| 1                | `{-101, -6}` |
| 2                | `{}`         |
| 3                | `{}`         |
| 4                | `{432, 17}`  |
| 5                | `{-788}`     |
| 6                | `{}`         |
| 7                | `{}`         |
| 8                | `{54, 84}`   |
| 9                | `{-195}`     |
