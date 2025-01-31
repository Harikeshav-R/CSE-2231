# Homework 11 - Hashing II

## **Answer 1**

```java
@Override
public int hashCode() {
    int hash = 0;
    for (int i = 0; i < rep.length(); i++) {
        char ch = rep.charAt(i);
        if (ch != '-') {
            hash = hash * 10 + Character.digit(ch, 10);
        }
    }
    return hash;
}
```

---

## **Answer 2**

### Answer i

They would produce different hash codes, causing them to be treated as distinct values even though they should be equivalent. This prevents correct lookups and potentially causes duplicate entries.

### Answer ii

We can convert alphabetic characters to their corresponding numeric values using the telephone keypad mapping before computing the hash. This makes sure that "OHIO" maps to "6446" so that "292-OHIO" and "292-6446" produce the same hash code.

### Answer iii

Convert all alphabetic characters to uppercase before applying the keypad mapping. This ensures that "292-ohio" is treated the same as "292-OHIO" and "292-6446".

Updated code:

```java
@Override
public int hashCode() {
    int hash = 0;
    for (int i = 0; i < rep.length(); i++) {
        char ch = rep.charAt(i);
        if (ch == '-') {
            continue; // Ignore hyphens
        }
        if (Character.isDigit(ch)) {
            hash = hash * 10 + Character.digit(ch, 10);
        } else if (Character.isLetter(ch)) {
            char upperCh = Character.toUpperCase(ch);
            int mappedDigit = letterToDigit(upperCh);
            hash = hash * 10 + mappedDigit;
        }
    }
    return hash;
}

/**
 * Maps an uppercase letter to its corresponding phone keypad digit.
 */
private int letterToDigit(char letter) {
    if (letter >= 'A' && letter <= 'C') return 2;
    if (letter >= 'D' && letter <= 'F') return 3;
    if (letter >= 'G' && letter <= 'I') return 4;
    if (letter >= 'J' && letter <= 'L') return 5;
    if (letter >= 'M' && letter <= 'O') return 6;
    if (letter >= 'P' && letter <= 'S') return 7;
    if (letter >= 'T' && letter <= 'V') return 8;
    if (letter >= 'W' && letter <= 'Z') return 9;
    return -1; // Unreachable if "letter" is a valid alphabet
}

```
