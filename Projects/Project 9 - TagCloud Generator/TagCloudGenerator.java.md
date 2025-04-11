# TagCloudGenerator.java

```java
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
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;
import components.utilities.Reporter;

/**
 * Generates an HTML tag cloud based on word frequency analysis from an input
 * file. Produces an HTML file displaying the top N most frequent words sized by
 * frequency and sorted alphabetically.
 *
 * @author Hari Rameshkumar
 * @author Praj Sachan
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
    }

    /**
     * Comparator that orders pairs by descending frequency value.
     */
    private static final class WordFrequencyDescendingComparator
            implements Comparator<Map.Pair<String, Integer>> {

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
        public int compare(Map.Pair<String, Integer> pair1,
                Map.Pair<String, Integer> pair2) {
            return pair2.value().compareTo(pair1.value());
        }
    }

    /**
     * Comparator that orders pairs alphabetically by key in ascending order.
     */
    private static final class WordAlphabeticalAscendingComparator
            implements Comparator<Map.Pair<String, Integer>> {

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
        public int compare(Map.Pair<String, Integer> pair1,
                Map.Pair<String, Integer> pair2) {
            return pair1.key().compareToIgnoreCase(pair2.key());
        }
    }

    /**
     * Set of delimiters used for separating words.
     */
    private static final Set<Character> DELIMITERS = new Set1L<Character>();

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
     * Reads words from a file and populates a map with their frequency counts.
     *
     * @param inputFilePath
     *            the path to the input file
     * @param allWordsFrequencyMap
     *            the map to store word-frequency pairs
     * @updates allWordsFrequencyMap
     * @requires inputFilePath is a valid file path, allWordsFrequencyMap is not
     *           null
     * @ensures allWordsFrequencyMap contains frequency counts for all words in
     *          the file
     */
    private static void readFileIntoAllWordsFrequencyMap(String inputFilePath,
            Map<String, Integer> allWordsFrequencyMap) {
        try {
            SimpleReader inputFileHandle = new SimpleReader1L(inputFilePath);
            Queue<String> wordsQueue = new Queue1L<String>();
            StringBuilder currentWord = new StringBuilder();

            while (!inputFileHandle.atEOS()) {
                Character character = inputFileHandle.read();

                if (DELIMITERS.contains(character)) {
                    if (currentWord.length() > 0) {
                        wordsQueue.enqueue(currentWord.toString().toLowerCase());
                        currentWord.setLength(0);
                    }
                } else {
                    currentWord.append(character);
                }
            }

            if (currentWord.length() > 0) {
                wordsQueue.enqueue(currentWord.toString().toLowerCase());
            }

            inputFileHandle.close();

            while (wordsQueue.length() > 0) {
                String word = wordsQueue.dequeue();

                if (allWordsFrequencyMap.hasKey(word)) {
                    allWordsFrequencyMap.replaceValue(word,
                            allWordsFrequencyMap.value(word) + 1);
                } else {
                    allWordsFrequencyMap.add(word, 1);
                }
            }
        } catch (java.lang.AssertionError e) {
            Reporter.fatalErrorToConsole("The given input file path is invalid!");
        }
    }

    /**
     * Extracts and stores the top N most frequent words from a word-frequency
     * map.
     *
     * @param wordsFrequencyMap
     *            the full word-frequency map
     * @param topNWordsFrequencyMap
     *            map to store the top N words
     * @param descendingFrequencySortingMachine
     *            sorting machine for sorting words by frequency
     * @param numberOfWords
     *            the number of top words to extract
     * @return an array containing [maxFrequency, minFrequency]
     * @updates topNWordsFrequencyMap, descendingFrequencySortingMachine
     * @requires wordsFrequencyMap, topNWordsFrequencyMap, and
     *           descendingFrequencySortingMachine are not null; numberOfWords
     *           <= wordsFrequencyMap.size()
     * @ensures topNWordsFrequencyMap contains top numberOfWords entries from
     *          wordsFrequencyMap by descending frequency
     * @ensures descendingFrequencySortingMachine is in extraction mode and is
     *          empty
     */
    private static int[] getTopNWordsFromWordFrequencyMap(
            Map<String, Integer> wordsFrequencyMap,
            Map<String, Integer> topNWordsFrequencyMap,
            SortingMachine<Map.Pair<String, Integer>> descendingFrequencySortingMachine,
            int numberOfWords) {
        Reporter.assertElseFatalError(numberOfWords <= wordsFrequencyMap.size(),
                "The top N number of words exceeds the number of unique words in the file!");

        for (Map.Pair<String, Integer> pair : wordsFrequencyMap) {
            descendingFrequencySortingMachine.add(pair);
        }

        descendingFrequencySortingMachine.changeToExtractionMode();

        int maxFrequency = 0;
        int minFrequency = 0;

        for (int i = 0; i < numberOfWords; i++) {
            Map.Pair<String, Integer> pair = descendingFrequencySortingMachine
                    .removeFirst();

            if (i == 0) {
                maxFrequency = pair.value();
            }

            if (i == numberOfWords - 1) {
                minFrequency = pair.value();
            }

            topNWordsFrequencyMap.add(pair.key(), pair.value());
        }

        return new int[] { maxFrequency, minFrequency };
    }

    /**
     * Populates a sorting machine with word-frequency pairs sorted
     * alphabetically.
     *
     * @param topNWordsFrequencyMap
     *            the map of top word-frequency pairs
     * @param wordAlphabeticalAscendingSortingMachine
     *            the sorting machine to populate
     * @updates wordAlphabeticalAscendingSortingMachine
     * @requires topNWordsFrequencyMap and
     *           wordAlphabeticalAscendingSortingMachine are not null
     * @ensures wordAlphabeticalAscendingSortingMachine contains all entries in
     *          topNWordsFrequencyMap
     */
    private static void populateWordAlphabeticalAscendingSortingMachine(
            Map<String, Integer> topNWordsFrequencyMap,
            SortingMachine<Map.Pair<String, Integer>> wordAlphabeticalAscendingSortingMachine) {

        for (Map.Pair<String, Integer> pair : topNWordsFrequencyMap) {
            wordAlphabeticalAscendingSortingMachine.add(pair);
        }
    }

    /**
     * Writes a predefined CSS style sheet to a file named {@code tagcloud.css}
     * located in the same directory as the HTML output file.
     *
     * @param originalOutputFilePath
     *            the path to the original HTML output file
     * @updates file system (writes to [parent directory of
     *          originalOutputFilePath]/tagcloud.css)
     * @requires originalOutputFilePath is a valid file path to an HTML file,
     *           and the parent directory exists and is writable
     * @ensures a new file named {@code tagcloud.css} is created in the same
     *          directory as {@code originalOutputFilePath} and contains the
     *          predefined tag cloud CSS source; otherwise, an error is reported
     */
    private static void writeCSSFile(String originalOutputFilePath) {
        String cssFileSource = """
                body { padding: 10px; margin: 10px;
                       background: #fff; color: #05e;
                       font-family:"Arial", Arial, Helvetica, sans-serif; }

                .cbox { padding: 12px; background: #d5d5d5; width: 700px; }
                .cdiv { margin-top: 0; padding-left: 7px; padding-right: 7px; }
                .cdiv span { text-direction: none; padding: 0px; margin: 3px; }
                .cdiv span:hover { color: #fff; background: #05e; }

                .f11 { font-size: 11px; line-height: 11px; }
                .f12 { font-size: 12px; line-height: 12px; }
                .f13 { font-size: 13px; line-height: 13px; }
                .f14 { font-size: 14px; line-height: 14px; }
                .f15 { font-size: 15px; line-height: 15px; }
                .f16 { font-size: 16px; line-height: 16px; }
                .f17 { font-size: 17px; line-height: 17px; }
                .f18 { font-size: 18px; line-height: 18px; }
                .f19 { font-size: 19px; line-height: 19px; }
                .f20 { font-size: 20px; line-height: 20px; }
                .f21 { font-size: 21px; line-height: 21px; }
                .f22 { font-size: 22px; line-height: 22px; }
                .f23 { font-size: 23px; line-height: 23px; }
                .f24 { font-size: 24px; line-height: 24px; }
                .f25 { font-size: 25px; line-height: 25px; }
                .f26 { font-size: 26px; line-height: 26px; }
                .f27 { font-size: 27px; line-height: 27px; }
                .f28 { font-size: 28px; line-height: 28px; }
                .f29 { font-size: 29px; line-height: 29px; }
                .f30 { font-size: 30px; line-height: 30px; }
                .f31 { font-size: 31px; line-height: 31px; }
                .f32 { font-size: 32px; line-height: 32px; }
                .f33 { font-size: 33px; line-height: 33px; }
                .f34 { font-size: 34px; line-height: 34px; }
                .f35 { font-size: 35px; line-height: 35px; }
                .f36 { font-size: 36px; line-height: 36px; }
                .f37 { font-size: 37px; line-height: 37px; }
                .f38 { font-size: 38px; line-height: 38px; }
                .f39 { font-size: 39px; line-height: 39px; }
                .f40 { font-size: 40px; line-height: 40px; }
                .f41 { font-size: 41px; line-height: 41px; }
                .f42 { font-size: 42px; line-height: 42px; }
                .f43 { font-size: 43px; line-height: 43px; }
                .f44 { font-size: 44px; line-height: 44px; }
                .f45 { font-size: 45px; line-height: 45px; }
                .f46 { font-size: 46px; line-height: 46px; }
                .f47 { font-size: 47px; line-height: 47px; }
                .f48 { font-size: 48px; line-height: 48px; }
                                """;

        String normalizedFilePath = originalOutputFilePath.replace('\\', '/');

        if (normalizedFilePath.endsWith("/")) {
            normalizedFilePath = normalizedFilePath.substring(0,
                    normalizedFilePath.length() - 1);
        }

        int lastSlashIndex = normalizedFilePath.lastIndexOf('/');
        String parentPath = normalizedFilePath.substring(0, lastSlashIndex);

        int secondLastSlashIndex = parentPath.lastIndexOf('/');
        String parentDirName = parentPath.substring(secondLastSlashIndex + 1);

        String cssFilePath = parentDirName + "/" + "tagcloud.css";

        try {
            SimpleWriter cssFileHandle = new SimpleWriter1L(cssFilePath);

            cssFileHandle.print(cssFileSource);
            cssFileHandle.close();
        } catch (java.lang.AssertionError e) {
            Reporter.fatalErrorToConsole(
                    "The given output file path " + cssFilePath + " cannot be opened.");
        }
    }

    /**
     * Writes an HTML file containing the tag cloud.
     *
     * @param inputFilePath
     *            original input file name to be shown in title
     * @param outputFilePath
     *            path to save the HTML file
     * @param wordAlphabeticalAscendingSortingMachine
     *            sorted words to be written
     * @param maxFrequency
     *            maximum frequency among top words
     * @param minFrequency
     *            minimum frequency among top words
     * @updates outputFilePath
     * @clears wordAlphabeticalAscendingSortingMachine
     * @requires outputFilePath is valid
     * @ensures outputFilePath contains HTML source for tag cloud
     */
    private static void writeHTMLFile(String inputFilePath, String outputFilePath,
            SortingMachine<Map.Pair<String, Integer>> wordAlphabeticalAscendingSortingMachine,
            int maxFrequency, int minFrequency) {
        int numberOfWords = wordAlphabeticalAscendingSortingMachine.size();

        if (wordAlphabeticalAscendingSortingMachine.isInInsertionMode()) {
            wordAlphabeticalAscendingSortingMachine.changeToExtractionMode();
        }

        StringBuilder wordList = new StringBuilder();

        while (wordAlphabeticalAscendingSortingMachine.size() > 0) {
            Map.Pair<String, Integer> pair = wordAlphabeticalAscendingSortingMachine
                    .removeFirst();
            int fontSize = MIN_FONT_SIZE + (int) (((double) (pair.value() - minFrequency)
                    / (maxFrequency - minFrequency)) * (MAX_FONT_SIZE - MIN_FONT_SIZE));

            String wordHTMLCode = """
                    <span style="cursor:default" class="f%d" title="count: %d">%s</span>\n
                    """;
            wordHTMLCode = String.format(wordHTMLCode, fontSize, pair.value(),
                    pair.key());

            wordList.append(wordHTMLCode);
        }

        wordAlphabeticalAscendingSortingMachine.clear();

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

        try {
            SimpleWriter outputFileHandle = new SimpleWriter1L(outputFilePath);

            outputFileHandle.print(finalHTMLSource);
            outputFileHandle.close();
        } catch (java.lang.AssertionError e) {
            Reporter.fatalErrorToConsole("The given output file path " + outputFilePath
                    + " cannot be opened.");
        }
    }

    /**
     * Main entry point for tag cloud generation. Prompts user for inputs,
     * extracts top N words, and generates output HTML.
     *
     * @param args
     *            command line arguments (unused)
     * @requires args.length = 0
     * @ensures Prompts for file paths and top word count, then creates HTML
     *          output
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        populateDelimitersSet();

        out.print("Enter the input file path: ");
        String inputFilePath = in.nextLine();

        out.print("Enter the output file path: ");
        String outputFilePath = in.nextLine();

        out.print("Enter the top N number of words to display: ");
        int numberOfWords = in.nextInteger();

        Map<String, Integer> allWordsFrequencyMap = new Map1L<String, Integer>();
        Map<String, Integer> topNWordsFrequencyMap = new Map1L<String, Integer>();
        SortingMachine<Map.Pair<String, Integer>> wordAlphabeticalAscendingSortingMachine = new SortingMachine1L<Map.Pair<String, Integer>>(
                WORD_ALPHABETICAL_ASCENDING_COMPARATOR);
        SortingMachine<Map.Pair<String, Integer>> wordFrequencyDescendingSortingMachine = new SortingMachine1L<Map.Pair<String, Integer>>(
                WORD_FREQUENCY_DESCENDING_COMPARATOR);

        readFileIntoAllWordsFrequencyMap(inputFilePath, allWordsFrequencyMap);
        int[] frequencies = getTopNWordsFromWordFrequencyMap(allWordsFrequencyMap,
                topNWordsFrequencyMap, wordFrequencyDescendingSortingMachine,
                numberOfWords);
        int maxFrequency = frequencies[0];
        int minFrequency = frequencies[1];

        populateWordAlphabeticalAscendingSortingMachine(topNWordsFrequencyMap,
                wordAlphabeticalAscendingSortingMachine);
        writeHTMLFile(inputFilePath, outputFilePath,
                wordAlphabeticalAscendingSortingMachine, maxFrequency, minFrequency);
        writeCSSFile(outputFilePath);

        in.close();
        out.close();
    }
}

```
