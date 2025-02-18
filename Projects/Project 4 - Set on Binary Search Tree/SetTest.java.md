## **SetTest.java**

```java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Hari Rameshkumar, Praj Sachan
 *
 */
public abstract class SetTest {
    /** Constructor Test. */
    protected abstract Set<String> constructorTest();

    /** Constructor Ref. */
    protected abstract Set<String> constructorRef();

    /** Create instance of test Set implementation. */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            set.add(s);
        }
        return set;
    }

    /** Create instance of reference Set implementation. */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            set.add(s);
        }
        return set;
    }

    /** Test constructor. */
    @Test
    public void testConstructor() {
        Set<String> setTest = this.constructorTest();
        Set<String> setRef = this.constructorRef();
        assertEquals(setRef.size(), setTest.size());
        assertFalse(setTest.contains("a"));
        assertFalse(setRef.contains("a"));
    }

    /** Test add method. */
    @Test
    public void testAdd() {
        Set<String> setTest = this.constructorTest();
        Set<String> setRef = this.constructorRef();

        setTest.add("a");
        setRef.add("a");
        assertEquals(setRef.size(), setTest.size());
        assertTrue(setTest.contains("a"));
        assertTrue(setRef.contains("a"));

        setTest.add("b");
        setRef.add("b");
        assertEquals(setRef.size(), setTest.size());
        assertTrue(setTest.contains("b"));
        assertTrue(setRef.contains("b"));

        assertThrows(AssertionError.class, () -> setTest.add("a"));

        setTest.add("c");
        setRef.add("c");
        assertEquals(setRef.size(), setTest.size());

        setTest.add("d");
        setRef.add("d");
        assertEquals(setRef.size(), setTest.size());
    }

    /** Test remove method. */
    @Test
    public void testRemove() {
        Set<String> setTest = this.createFromArgsTest("x", "y", "z", "w", "v");
        Set<String> setRef = this.createFromArgsRef("x", "y", "z", "w", "v");

        setTest.remove("y");
        setRef.remove("y");
        assertEquals(setRef.size(), setTest.size());
        assertFalse(setTest.contains("y"));
        assertFalse(setRef.contains("y"));

        setTest.remove("z");
        setRef.remove("z");
        assertEquals(setRef.size(), setTest.size());

        assertThrows(AssertionError.class, () -> setTest.remove("y"));

        setTest.remove("x");
        setRef.remove("x");
        assertEquals(setRef.size(), setTest.size());
    }

    /** Test removeAny method. */
    @Test
    public void testRemoveAny() {
        Set<String> setTest = this.createFromArgsTest("x", "y", "z", "w", "v");
        Set<String> setRef = this.createFromArgsRef("x", "y", "z", "w", "v");

        String removedTest = setTest.removeAny();
        String removedRef = setRef.removeAny();
        assertEquals(setRef.size(), setTest.size());
        assertFalse(setTest.contains(removedTest));
        assertFalse(setRef.contains(removedRef));

        assertThrows(AssertionError.class, () -> this.constructorTest().removeAny());
    }

    /** Test contains method. */
    @Test
    public void testContains() {
        Set<String> setTest = this.createFromArgsTest("apple", "banana", "cherry", "date",
                "elderberry");
        Set<String> setRef = this.createFromArgsRef("apple", "banana", "cherry", "date",
                "elderberry");

        assertTrue(setTest.contains("banana"));
        assertTrue(setRef.contains("banana"));

        assertFalse(setTest.contains("fig"));
        assertFalse(setRef.contains("fig"));

        assertThrows(AssertionError.class, () -> setTest.contains(null));

        assertTrue(setTest.contains("apple"));
        assertTrue(setRef.contains("apple"));
    }

    /** Test size method. */
    @Test
    public void testSize() {
        Set<String> setTest = this.createFromArgsTest("one", "two", "three", "four",
                "five");
        Set<String> setRef = this.createFromArgsRef("one", "two", "three", "four",
                "five");

        assertEquals(setRef.size(), setTest.size());

        setTest.add("six");
        setRef.add("six");
        assertEquals(setRef.size(), setTest.size());

        setTest.remove("two");
        setRef.remove("two");
        assertEquals(setRef.size(), setTest.size());

        assertEquals(0, this.constructorTest().size());

        setTest.add("seven");
        setRef.add("seven");
        assertEquals(setRef.size(), setTest.size());
    }
}

```
