# Homework 29 - Evaluation of Boolean Expressions (Recursive Descent Parser)

```java
private static boolean parseBoolExpr(Queue<String> tokens) {
    String token = tokens.dequeue();

    switch (token) {
        case "T":
            return true;
        case "F":
            return false;
        case "NOT":
            tokens.dequeue();  // Remove "("
            boolean value = !parseBoolExpr(tokens);
            tokens.dequeue();  // Remove ")"
            return value;
        case "(":
            boolean left = parseBoolExpr(tokens);
            String op = tokens.dequeue();
            boolean right = parseBoolExpr(tokens);
            tokens.dequeue();  // Remove ")"

            if (op.equals("AND")) {
                return left && right;
            } else {
                return left || right;
            }
        default:
            return false;
    }
}

```
