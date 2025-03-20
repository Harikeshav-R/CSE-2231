# Homework 26 - Tokenizer

### Question 1

```java
private static String nextWordOrSeparator(String text, int position) {
    boolean isSeparator = SEPARATORS.indexOf(text.charAt(position)) != -1;
    int end = position;

    while (end < text.length() && (SEPARATORS.indexOf(text.charAt(end)) != -1) == isSeparator) {
        end++;
    }

    return text.substring(position, end);
}
```

---

### Question 2

```java
public static Queue<String> tokens(SimpleReader in) {
    Queue<String> tokenQueue = new Queue1L<String>();

    while (!in.atEOS()) {
        String line = in.nextLine();
        int position = 0;

        while (position < line.length()) {
            String token = nextWordOrSeparator(line, position);
            position += token.length();

            if (!SEPARATORS.contains(String.valueOf(token.charAt(0)))) {
                tokenQueue.enqueue(token);
            }
        }
    }

    tokenQueue.enqueue(END_OF_INPUT);

    return tokenQueue;
}
```
