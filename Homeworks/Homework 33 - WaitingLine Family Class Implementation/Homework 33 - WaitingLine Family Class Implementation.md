## Homework 33 - WaitingLine Family Class Implementation

```java
@Override
public String removeFromFront() {
    return this.removeFromLine(0);
}

@Override
public int removeName(String x) {
    for (int i = 0; i < this.lengthOfLine(); i++) {
        if (this.get(i).equals(x)) {
            this.removeFromLine(i);
            return i;
        }
    }
    // Should not reach here because of precondition: this /= <> and contains x
    return -1;
}

@Override
public String front() {
    return this.get(0);
}

@Override
public int positionInLine(String x) {
    for (int i = 0; i < this.lengthOfLine(); i++) {
        if (this.get(i).equals(x)) {
            return i;
        }
    }
    // Should not reach here because of precondition: this contains x
    return -1;
}

```
