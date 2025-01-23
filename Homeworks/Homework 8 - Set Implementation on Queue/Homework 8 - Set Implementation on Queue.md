# Homework 8 - Set Implementation on Queue

### **Answer 1**

```java
/**
* Finds {@code x} in {@code q} and, if such exists, moves it to the front
* of {@code q}.
*
* @param <T>
*            type of {@code Queue} entries
* @param q
*            the {@code Queue} to be searched
* @param x
*            the entry to be searched for
* @updates q
* @ensures <pre>
* perms(q, #q)  and
* if <x> is substring of q
*  then <x> is prefix of q
* </pre>
*/
private static <T> void moveToFront(Queue<T> q, T x) {
    int size = q.length();
    boolean found = false;

    for (int i = 0; i < size; i++) {
        T element = q.dequeue();

        if (!found && element.equals(x)) {
            found = true;
        } else {
            q.enqueue(element);
        }
    }

    if (found) {
        q.enqueue(x);

        for (int i = 0; i < size; i++) {
            q.enqueue(q.dequeue());
        }
    }
}
```

---

### **Answer 2**

```java
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Hari Rameshkumar
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // Test for the constructor with an empty set
    @Test
    public void testConstructorEmpty() {
        Set<String> testSet = this.constructorTest();
        Set<String> expectedSet = this.constructorRef();
        assertEquals(expectedSet, testSet);
    }

    // Test for add() - adding a new element to a non-empty set
    @Test
    public void testAddNewElementToNonEmptySet() {
        // Setup
        Set<String> testSet = this.createFromArgsTest("apple", "banana");
        Set<String> expectedSet = this.createFromArgsRef("apple", "banana");

        // Call
        testSet.add("cherry");

        // Evaluation
        expectedSet.add("cherry");
        assertEquals(expectedSet, testSet);
    }

    // Test for remove() - removing an existing element from the set
    @Test
    public void testRemoveExistingElement() {
        // Setup
        Set<String> testSet = this.createFromArgsTest("apple", "banana", "cherry");
        Set<String> expectedSet = this.createFromArgsRef("apple", "banana", "cherry");

        // Call
        String removedValue = testSet.remove("banana");

        // Evaluation
        assertEquals("banana", removedValue);
        expectedSet.remove("banana");
        assertEquals(expectedSet, testSet);
    }

    // Test for removeAny() - removing any element from a non-empty set
    @Test
    public void testRemoveAnySizeThree() {
        // Setup
        Set<String> testSet = this.createFromArgsTest("apple", "banana", "cherry");
        Set<String> expectedSet = this.createFromArgsRef("apple", "banana", "cherry");

        // Call
        String removedValue = testSet.removeAny();

        // Evaluation
        assertEquals(true, expectedSet.contains(removedValue));
        expectedSet.remove(removedValue);
        assertEquals(expectedSet, testSet);
    }

    // Test for removeAny() - removing an element from a set of size 1
    @Test
    public void testRemoveAnySizeOne() {
        // Setup
        Set<String> testSet = this.createFromArgsTest("apple");
        Set<String> expectedSet = this.createFromArgsRef("apple");

        // Call
        String removedValue = testSet.removeAny();

        // Evaluation
        assertEquals(true, expectedSet.contains(removedValue));
        expectedSet.remove(removedValue);
        assertEquals(expectedSet, testSet);
    }

    // Test for contains() - checking if an element is in the set
    @Test
    public void testContains() {
        // Setup
        Set<String> testSet = this.createFromArgsTest("apple", "banana");
        Set<String> expectedSet = this.createFromArgsRef("apple", "banana");

        // Evaluation
        assertEquals(true, testSet.contains("apple"));
        assertEquals(false, testSet.contains("cherry"));
    }

    // Test for size() - checking the size of the set
    @Test
    public void testSize() {
        // Setup
        Set<String> testSet = this.createFromArgsTest("apple", "banana", "cherry");
        Set<String> expectedSet = this.createFromArgsRef("apple", "banana", "cherry");

        // Evaluation
        assertEquals(3, testSet.size());
        testSet.remove("banana");
        expectedSet.remove("banana");
        assertEquals(expectedSet.size(), testSet.size());
    }
}

```
