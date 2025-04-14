# Homework 36 - Java File IO

## Question 1

```java
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public final class FileCopy {

    private FileCopy() {
        // Private constructor to prevent instantiation
    }

    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];

        SimpleReader in = new SimpleReader1L(inputFileName);
        SimpleWriter out = new SimpleWriter1L(outputFileName);

        while (!in.atEOS()) {
            String line = in.nextLine();
            out.println(line);
        }

        in.close();
        out.close();
    }
}
```

---

## Question 2

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public final class FileCopy {

    private FileCopy() {
        // Private constructor to prevent instantiation
    }

    public static void main(String[] args) throws IOException {
        String inputFileName = args[0];
        String outputFileName = args[1];

        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));

        String line = reader.readLine();
        while (line != null) {
            writer.println(line);
            line = reader.readLine();
        }

        reader.close();
        writer.close();
    }
}

```

---

## Question 3

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public final class FileCopy {

    private FileCopy() {
        // Private constructor to prevent instantiation
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java FileCopy <input file> <output file>");
            return;
        }

        String inputFileName = args[0];
        String outputFileName = args[1];

        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(inputFileName));
            writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));

            String line = reader.readLine();
            while (line != null) {
                writer.println(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }

        if (reader != null) {
            reader.close();
        }

        if (writer != null) {
            writer.close();
        }
    }
}
```
