# Homework 28 - Evaluation of Arithmetic Expressions (Recursive Descent Parser)

```java
public static int valueOfExpr(StringBuilder source) {
    int value = valueOfTerm(source);
    while (source.length() > 0 && (source.charAt(0) == '+' || source.charAt(0) == '-')) {
        char op = source.charAt(0);
        source.deleteCharAt(0);
        int nextTerm = valueOfTerm(source);
        if (op == '+') {
            value = value + nextTerm;
        } else {
            value = value - nextTerm;
        }
    }
    return value;
}

private static int valueOfTerm(StringBuilder source) {
    int value = valueOfFactor(source);
    while (source.length() > 0 && (source.charAt(0) == '*' || source.charAt(0) == '/')) {
        char op = source.charAt(0);
        source.deleteCharAt(0);
        int nextFactor = valueOfFactor(source);
        if (op == '*') {
            value = value * nexFactor;
        } else {
            value = value / nextFactor;
        }
    }
    return value;
}

private static int valueOfFactor(StringBuilder source) {
    if (source.charAt(0) == '(') {
        source.deleteCharAt(0);
        int value = valueOfExpr(source);
        source.deleteCharAt(0); // Remove closing ')'
        return value;
    } else {
        return valueOfDigitSeq(source);
    }
}

private static int valueOfDigitSeq(StringBuilder source) {
    int value = 0;
    while (source.length() > 0 && Character.isDigit(source.charAt(0))) {
        value = value * 10 + valueOfDigit(source);
    }
    return value;
}

private static int valueOfDigit(StringBuilder source) {
    char digitChar = source.charAt(0);
    source.deleteCharAt(0);
    return Character.digit(digitChar, 10);
}
```
