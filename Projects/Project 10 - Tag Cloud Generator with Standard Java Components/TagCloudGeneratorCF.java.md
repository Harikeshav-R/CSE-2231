# TagCloudGeneratorCF.java

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * Generates an HTML tag cloud based on word frequency analysis from an input
 * file. Produces an HTML file displaying the top N most frequent words sized by
 * frequency and sorted alphabetically.
 *
 * @author Hari Rameshkumar
 * @author Praj Sachan
 */
public final class TagCloudGeneratorCF {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGeneratorCF() {
    }

    /**
     * Comparator that orders word-frequency pairs in descending order of
     * frequency. If two entries have the same frequency, they are ordered
     * alphabetically by key.
     *
     * @implements Comparator<Map.Entry<String, Integer>>
     * @requires pair1 and pair2 are not null and have non-null keys and values
     * @ensures compare = a negative integer if pair1 is "greater" than pair2 in
     *          descending frequency order, zero if equal, or positive if less
     */
    private static final class WordFrequencyDescendingComparator
            implements Comparator<Map.Entry<String, Integer>> {

        /**
         * Compares two word-frequency pairs by descending frequency.
         *
         * @param pair1
         *            the first pair to compare
         * @param pair2
         *            the second pair to compare
         * @return an integer representing the comparison result
         * @requires pair1 and pair2 are not null
         * @ensures compare = sign of (pair2.value - pair1.value)
         */
        @Override
        public int compare(Map.Entry<String, Integer> pair1,
                Map.Entry<String, Integer> pair2) {
            int comparisonValue;
            int freqCompare = pair2.getValue().compareTo(pair1.getValue());
            if (freqCompare != 0) {
                comparisonValue = freqCompare;
            } else {
                comparisonValue = pair1.getKey().compareTo(pair2.getKey());
            }
            return comparisonValue;
        }
    }

    /**
     * Comparator that orders word-frequency pairs alphabetically by word
     * (ignoring case). If two words are identical alphabetically, then orders
     * them by ascending frequency.
     *
     * @implements Comparator<Map.Entry<String, Integer>>
     * @requires pair1 and pair2 are not null and have non-null keys and values
     * @ensures compare = a negative integer if pair1 is "less" than pair2 in
     *          alphabetical order, zero if equal, or positive if greater;
     *          breaks ties by comparing frequency in ascending order
     */
    private static final class WordAlphabeticalAscendingComparator
            implements Comparator<Map.Entry<String, Integer>> {

        /**
         * Compares two word-frequency pairs by key alphabetically.
         *
         * @param pair1
         *            the first pair to compare
         * @param pair2
         *            the second pair to compare
         * @return an integer representing the comparison result
         * @requires pair1 and pair2 are not null
         * @ensures compare = result of ignoring-case alphabetical comparison of
         *          keys
         */
        @Override
        public int compare(Map.Entry<String, Integer> pair1,
                Map.Entry<String, Integer> pair2) {
            int comparisonValue;
            int keyCompare = pair1.getKey().compareToIgnoreCase(pair2.getKey());
            if (keyCompare != 0) {
                comparisonValue = keyCompare;
            } else {
                comparisonValue = pair1.getValue().compareTo(pair2.getValue());
            }
            return comparisonValue;
        }
    }

    /**
     * Set of delimiters used for separating words.
     */
    private static final Set<Character> DELIMITERS = new HashSet<Character>();

    /**
     * Maximum font size available to the program provided by the CSS file.
     */
    private static final int MAX_FONT_SIZE = 48;

    /**
     * Minimum font size available to the program provided by the CSS file.
     */
    private static final int MIN_FONT_SIZE = 11;

    /**
     * Comparator instance to sort word and frequency map pairs in descending
     * order of frequency.
     */
    private static final WordFrequencyDescendingComparator WORD_FREQUENCY_DESCENDING_COMPARATOR = new WordFrequencyDescendingComparator();

    /**
     * Comparator instance to sort word and frequency map pairs in ascending
     * order of alphabetical order.
     */
    private static final WordAlphabeticalAscendingComparator WORD_ALPHABETICAL_ASCENDING_COMPARATOR = new WordAlphabeticalAscendingComparator();

    /**
     * Populates the set of characters to be used as word delimiters.
     *
     * @updates DELIMITERS
     * @ensures DELIMITERS contains predefined punctuation and whitespace
     *          characters
     */
    private static void populateDelimitersSet() {
        char[] delimiters = { ' ', '\t', '\n', '\r', ',', '-', '.', '!', '?', '[', ']',
                '\'', ';', ':', '/', '(', ')' };

        for (char delimiter : delimiters) {
            DELIMITERS.add(delimiter);
        }
    }

    /**
     * Reads the contents of a file and populates a map with the frequency
     * counts of each word, converting all words to lowercase and using a fixed
     * set of delimiters to split the words.
     *
     * @param inputFilePath
     *            the path to the input file
     * @param allWordsFrequencyMap
     *            the map that stores frequency of each word
     * @updates allWordsFrequencyMap
     * @requires inputFilePath is a path to a valid, readable text file and
     *           allWordsFrequencyMap is not null
     * @ensures allWordsFrequencyMap contains an entry for every word in the
     *          input file (converted to lowercase), with its value equal to the
     *          number of times it appears
     */
    private static void readFileIntoAllWordsFrequencyMap(String inputFilePath,
            Map<String, Integer> allWordsFrequencyMap) {
        BufferedReader inputFileHandle = null;
        Queue<String> wordsQueue = new LinkedList<String>();

        try {
            inputFileHandle = new BufferedReader(new FileReader(inputFilePath));
            StringBuilder currentWord = new StringBuilder();

            int character = inputFileHandle.read();

            while (character != -1) {
                if (DELIMITERS.contains((char) character)) {
                    if (currentWord.length() > 0) {
                        wordsQueue.add(currentWord.toString().toLowerCase());
                        currentWord.setLength(0);
                    }
                } else {
                    currentWord.append((char) character);
                }

                character = inputFileHandle.read();
            }

            if (currentWord.length() > 0) {
                wordsQueue.add(currentWord.toString().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("I/O Exception encountered: " + e.getMessage());
            return;
        }

        if (inputFileHandle != null) {
            try {
                inputFileHandle.close();
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
                return;
            }
        }

        while (wordsQueue.size() > 0) {
            String word = wordsQueue.remove();

            if (allWordsFrequencyMap.containsKey(word)) {
                int frequency = allWordsFrequencyMap.get(word);
                allWordsFrequencyMap.replace(word, frequency + 1);

            } else {
                allWordsFrequencyMap.put(word, 1);
            }
        }
    }

    /**
     * Selects the top {@code numberOfWords} most frequent words from the input
     * frequency map and copies them into a new map. Also determines and returns
     * the maximum and minimum frequencies among the selected words.
     *
     * @param wordsFrequencyMap
     *            the full map of all word frequencies
     * @param topNWordsFrequencyMap
     *            the map to store the top N most frequent word-frequency pairs
     * @param numberOfWords
     *            the number of most frequent words to extract
     * @return an array of two integers: [maxFrequency, minFrequency]
     * @updates topNWordsFrequencyMap
     * @requires wordsFrequencyMap and topNWordsFrequencyMap are not null and 0
     *           < numberOfWords <= size of wordsFrequencyMap
     * @ensures topNWordsFrequencyMap contains numberOfWords entries with the
     *          highest frequencies from wordsFrequencyMap and the returned
     *          array contains the maximum and minimum frequencies among the
     *          selected entries
     */
    private static int[] getTopNWordsFromWordFrequencyMap(
            Map<String, Integer> wordsFrequencyMap,
            Map<String, Integer> topNWordsFrequencyMap, int numberOfWords) {

        List<Map.Entry<String, Integer>> descendingFrequencyList = new ArrayList<Map.Entry<String, Integer>>();

        for (Map.Entry<String, Integer> pair : wordsFrequencyMap.entrySet()) {
            descendingFrequencyList.add(pair);
        }

        Collections.sort(descendingFrequencyList, WORD_FREQUENCY_DESCENDING_COMPARATOR);

        int maxFrequency = 0;
        int minFrequency = 0;

        for (int i = 0; i < numberOfWords; i++) {
            Map.Entry<String, Integer> entry = descendingFrequencyList.remove(0);

            if (i == 0) {
                maxFrequency = entry.getValue();
            }

            if (i == numberOfWords - 1) {
                minFrequency = entry.getValue();
            }

            topNWordsFrequencyMap.put(entry.getKey(), entry.getValue());
        }

        return new int[] { maxFrequency, minFrequency };
    }

    /**
     * Generates an HTML file that displays a tag cloud from the provided
     * word-frequency map. Each word is displayed using a font size scaled
     * between {@code MIN_FONT_SIZE} and {@code MAX_FONT_SIZE} based on its
     * frequency relative to the most and least frequent words.
     *
     * @param inputFilePath
     *            the original input file path (used in the HTML title/header)
     * @param outputFilePath
     *            the path to which the HTML file will be written
     * @param topNWordsFrequencyMap
     *            a map of word-frequency pairs to be included in the tag cloud
     * @param maxFrequency
     *            the maximum word frequency in the map
     * @param minFrequency
     *            the minimum word frequency in the map
     * @updates outputFilePath
     * @requires topNWordsFrequencyMap is not null, inputFilePath and
     *           outputFilePath are valid non-empty strings, maxFrequency >=
     *           minFrequency >= 0, and topNWordsFrequencyMap is non-empty
     * @ensures outputFilePath is updated with valid HTML that represents a tag
     *          cloud of the given words sized proportionally to their frequency
     */
    private static void writeHTMLFile(String inputFilePath, String outputFilePath,
            Map<String, Integer> topNWordsFrequencyMap, int maxFrequency,
            int minFrequency) {
        int numberOfWords = topNWordsFrequencyMap.size();

        StringBuilder wordList = new StringBuilder();

        List<Map.Entry<String, Integer>> ascendingAlphabeticalList = new ArrayList<Map.Entry<String, Integer>>();

        for (Map.Entry<String, Integer> entry : topNWordsFrequencyMap.entrySet()) {
            ascendingAlphabeticalList.add(entry);
        }

        Collections.sort(ascendingAlphabeticalList,
                WORD_ALPHABETICAL_ASCENDING_COMPARATOR);

        for (Map.Entry<String, Integer> entry : ascendingAlphabeticalList) {
            int fontSize = MIN_FONT_SIZE
                    + (int) (((double) (entry.getValue() - minFrequency)
                            / (maxFrequency - minFrequency))
                            * (MAX_FONT_SIZE - MIN_FONT_SIZE));

            String wordHTMLCode = """
                    <span style="cursor:default" class="f%d" title="count: %d">%s</span>\n
                    """;
            wordHTMLCode = String.format(wordHTMLCode, fontSize, entry.getValue(),
                    entry.getKey());

            wordList.append(wordHTMLCode);
        }

        String finalHTMLSource = """
                <html>
                    <head>
                        <title>
                            Top %d words in %s
                        </title>
                        <link href="https://cse22x1.engineering.osu.edu/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css" rel="stylesheet" type="text/css">
                        <link href="tagcloud.css" rel="stylesheet" type="text/css">
                    </head>
                    <body>
                        <h2>
                            Top %d words in %s
                        </h2>
                        <hr>
                        <div class="cdiv">
                            <p class="cbox">
                                %s
                            </p>
                        </div>
                    </body>
                </html>
                                """;

        finalHTMLSource = String.format(finalHTMLSource, numberOfWords, inputFilePath,
                numberOfWords, inputFilePath, wordList.toString());

        PrintWriter outputFileHandle = null;
        try {
            outputFileHandle = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFilePath)));
        } catch (IOException e) {
            System.err.println(e);
            return;
        }

        if (outputFileHandle != null) {
            outputFileHandle.print(finalHTMLSource);
            outputFileHandle.close();
        }
    }

    /**
     * Main entry point for the Tag Cloud Generator program. Prompts the user
     * for an input file path, output file path, and a number of top frequent
     * words. Then it generates an HTML file containing a visual tag cloud of
     * the most frequent words found in the input file.
     *
     * @param args
     *            command-line arguments (not used)
     * @requires args.length = 0
     * @ensures Prompts the user for input file, output file, and word count;
     *          creates a tag cloud in HTML format and writes it to the
     *          specified output file
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        populateDelimitersSet();

        System.out.print("Enter the input file path: ");
        String inputFilePath = in.nextLine();

        System.out.print("Enter the output file path: ");
        String outputFilePath = in.nextLine();

        System.out.print("Enter the top N number of words to display: ");
        int numberOfWords = in.nextInt();

        Map<String, Integer> allWordsFrequencyMap = new HashMap<String, Integer>();
        Map<String, Integer> topNWordsFrequencyMap = new HashMap<String, Integer>();

        readFileIntoAllWordsFrequencyMap(inputFilePath, allWordsFrequencyMap);
        int[] frequencies = getTopNWordsFromWordFrequencyMap(allWordsFrequencyMap,
                topNWordsFrequencyMap, numberOfWords);
        int maxFrequency = frequencies[0];
        int minFrequency = frequencies[1];

        writeHTMLFile(inputFilePath, outputFilePath, topNWordsFrequencyMap, maxFrequency,
                minFrequency);

        in.close();
    }

}

```
