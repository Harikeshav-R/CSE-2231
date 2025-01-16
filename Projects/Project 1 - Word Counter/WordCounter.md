```java
import java.nio.file.Paths;
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * A program that reads a text file, counts the occurrences of each word, and
 * generates an HTML report displaying the word counts in a table. The words are
 * listed in alphabetical order, and the output file contains a well-structured
 * and styled HTML document.
 *
 */
public final class WordCounter {

    /**
     * The main method drives the program.
     *
     * @param args
     *            Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        SimpleWriter writer = new SimpleWriter1L();
        SimpleReader reader = new SimpleReader1L();

        writer.print("Enter the name of the input file (with path): ");
        String inputFileName = reader.nextLine();

        writer.print("Enter the name of the output file (with path): ");
        String outputFileName = reader.nextLine();

        String content = readFileAsString(Paths.get(inputFileName).toString());
        Map<String, Integer> wordCounts = countWords(content);
        Queue<String> sortedKeys = sortKeys(wordCounts);

        writeHtmlOutput(inputFileName, outputFileName, wordCounts, sortedKeys);

        writer.println("HTML file successfully created: " + outputFileName);

        writer.close();
        reader.close();
    }

    /**
     * Reads the content of a file and returns it as a single string.
     *
     * @param filePath
     *            The path of the file to read.
     * @return The content of the file as a string.
     */
    public static String readFileAsString(String filePath) {
        StringBuilder content = new StringBuilder();

        // Open the file for reading
        SimpleReader1L reader = new SimpleReader1L(filePath);

        // Read each line and append to the content
        while (!reader.atEOS()) {
            content.append(reader.nextLine()).append("\n");
        }

        // Close the reader to release resources
        reader.close();

        return content.toString();
    }

    /**
     * Counts the occurrences of each word in a given text.
     *
     * @param content
     *            The input text to analyze.
     * @return A map containing words as keys and their counts as values.
     */
    private static Map<String, Integer> countWords(String content) {
        Set<Character> separators = new Set1L<>();
        char[] separatorArray = { ' ', '\n', '\r', '\t', ',', '.', '!', '?', ';', ':',
                '(', ')', '[', ']', '{', '}', '"', '\'', '-', '_', '/', '\\' };

        // Add all separators to the set
        for (char separator : separatorArray) {
            separators.add(separator);
        }

        Map<String, Integer> wordCounts = new Map1L<>();
        StringBuilder word = new StringBuilder();

        // Iterate over each character in the text
        for (char c : content.toCharArray()) {
            if (separators.contains(c)) {
                // Process the word if a separator is encountered
                if (word.length() > 0) {
                    String normalizedWord = word.toString().toLowerCase();
                    if (wordCounts.hasKey(normalizedWord)) {
                        wordCounts.replaceValue(normalizedWord,
                                wordCounts.value(normalizedWord) + 1);
                    } else {
                        wordCounts.add(normalizedWord, 1);
                    }
                    word.setLength(0);
                }
            } else {
                word.append(c);
            }
        }

        // Process the final word if any
        if (word.length() > 0) {
            String normalizedWord = word.toString().toLowerCase();
            if (wordCounts.hasKey(normalizedWord)) {
                wordCounts.replaceValue(normalizedWord,
                        wordCounts.value(normalizedWord) + 1);
            } else {
                wordCounts.add(normalizedWord, 1);
            }
        }

        return wordCounts;
    }

    /**
     * Sorts the keys of a map alphabetically.
     *
     * @param wordCounts
     *            The map containing the words and their counts.
     * @return A queue containing the sorted keys.
     */
    private static Queue<String> sortKeys(Map<String, Integer> wordCounts) {
        Queue<String> wordsQueue = new Queue1L<>();

        // Add all keys from the map to the queue
        for (Map.Pair<String, Integer> entry : wordCounts) {
            wordsQueue.enqueue(entry.key());
        }

        // Sort the keys alphabetically
        Comparator<String> alphabeticalOrderComparator = new Comparator<>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        };

        wordsQueue.sort(alphabeticalOrderComparator);

        return wordsQueue;
    }

    /**
     * Writes an HTML file displaying the word count results.
     *
     * @param inputFileName
     *            The name of the input file.
     * @param outputFileName
     *            The name of the output file.
     * @param wordCounts
     *            The map containing the words and their counts.
     * @param sortedKeys
     *            A queue containing the sorted keys.
     */
    private static void writeHtmlOutput(String inputFileName, String outputFileName,
            Map<String, Integer> wordCounts, Queue<String> sortedKeys) {
        SimpleWriter writer = new SimpleWriter1L(Paths.get(outputFileName).toString());

        // Write the HTML structure and styles
        writer.println("<!DOCTYPE html>");
        writer.println("<html lang=\"en\">");
        writer.println("<head>");
        writer.println("<meta charset=\"UTF-8\">");
        writer.println(
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        writer.println("<title>Word Count</title>");
        writer.println("<style>");
        writer.println(
                "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f8f9fa; color: #343a40; }");
        writer.println(
                "header { background-color: #007bff; color: white; padding: 20px; text-align: center; }");
        writer.println(
                "table { border-collapse: collapse; width: 80%; margin: 20px auto; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
        writer.println(
                "th, td { padding: 12px; text-align: left; border: 1px solid #dee2e6; }");
        writer.println("th { background-color: #007bff; color: white; }");
        writer.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        writer.println("tr:hover { background-color: #d6e9f9; }");
        writer.println("</style>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<header>");
        writer.println("<h1>Word Count Report</h1>");
        writer.println("<h2>Input File: " + inputFileName + "</h2>");
        writer.println("</header>");
        writer.println("<main>");
        writer.println("<table>");
        writer.println("<thead>");
        writer.println("<tr><th>Word</th><th>Count</th></tr>");
        writer.println("</thead>");
        writer.println("<tbody>");

        // Write the sorted keys and their counts in a table
        while (sortedKeys.length() > 0) {
            String word = sortedKeys.dequeue();
            writer.println("<tr><td>" + word + "</td><td>" + wordCounts.value(word)
                    + "</td></tr>");
        }

        writer.println("</tbody>");
        writer.println("</table>");
        writer.println("</main>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
```
