# Homework 7 - Sequence Implementation on Stack

### **Answer 1**

```java
private static <T> void setLengthOfLeftStack(Stack<T> leftStack, Stack<T> rightStack, int newLeftLength) {
    int currentLeftLength = leftStack.size();

    // If the current length of leftStack is greater than the desired length
    if (currentLeftLength > newLeftLength) {
        // Move items from leftStack to rightStack
        while (currentLeftLength > newLeftLength) {
            rightStack.push(leftStack.pop());
            currentLeftLength--;
        }
    }
    // If the current length of leftStack is less than the desired length
    else if (currentLeftLength < newLeftLength) {
        // Move items from rightStack to leftStack
        while (currentLeftLength < newLeftLength) {
            leftStack.push(rightStack.pop());
            currentLeftLength++;
        }
    }
    // If the lengths are equal, do nothing
}
```

---

### **Answer 2**

```java
import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    @Test
    public void testConstructor() {
        Sequence<String> seqTest = this.constructorTest();
        Sequence<String> seqRef = this.constructorRef();
        assertEquals(seqRef, seqTest);
    }

    @Test
    public void testAddToEmptySequence() {
        Sequence<String> seqTest = this.createFromArgsTest();
        Sequence<String> seqRef = this.createFromArgsRef();

        seqTest.add(0, "A");
        seqRef.add(0, "A");

        assertEquals(seqRef, seqTest);
    }

    @Test
    public void testAddToNonEmptySequence() {
        Sequence<String> seqTest = this.createFromArgsTest("A", "B");
        Sequence<String> seqRef = this.createFromArgsRef("A", "B");

        seqTest.add(1, "C");
        seqRef.add(1, "C");

        assertEquals(seqRef, seqTest);
    }

    @Test
    public void testRemoveFromSingleElementSequence() {
        Sequence<String> seqTest = this.createFromArgsTest("A");
        Sequence<String> seqRef = this.createFromArgsRef("A");

        String removedTest = seqTest.remove(0);
        String removedRef = seqRef.remove(0);

        assertEquals(removedRef, removedTest);
        assertEquals(seqRef, seqTest);
    }

    @Test
    public void testRemoveFromMultipleElementSequence() {
        Sequence<String> seqTest = this.createFromArgsTest("A", "B", "C");
        Sequence<String> seqRef = this.createFromArgsRef("A", "B", "C");

        String removedTest = seqTest.remove(1);
        String removedRef = seqRef.remove(1);

        assertEquals(removedRef, removedTest);
        assertEquals(seqRef, seqTest);
    }

    @Test
    public void testLengthOfEmptySequence() {
        Sequence<String> seqTest = this.createFromArgsTest();
        Sequence<String> seqRef = this.createFromArgsRef();

        int lengthTest = seqTest.length();
        int lengthRef = seqRef.length();

        assertEquals(lengthRef, lengthTest);
    }

    @Test
    public void testLengthOfNonEmptySequence() {
        Sequence<String> seqTest = this.createFromArgsTest("A", "B", "C");
        Sequence<String> seqRef = this.createFromArgsRef("A", "B", "C");

        int lengthTest = seqTest.length();
        int lengthRef = seqRef.length();

        assertEquals(lengthRef, lengthTest);
    }
}
```
