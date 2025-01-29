# Homework 9 - Map Implementation on Queue

## **Answer 1**

```java
/**
* Finds pair with first component {@code key} and, if such exists, moves it
* to the front of {@code q}.
*
* @param <K>
*            type of {@code Pair} key
* @param <V>
*            type of {@code Pair} value
* @param q
*            the {@code Queue} to be searched
* @param key
*            the key to be searched for
* @updates q
* @ensures <pre>
* perms(q, #q)  and
* if there exists value: V (<(key, value)> is substring of q)
*  then there exists value: V (<(key, value)> is prefix of q)
* </pre>
*/
private static <K, V> void moveToFront(Queue<Pair<K, V>> q, K key) {
  for (int i = q.length(); i > 0 && !key.equals((q.front()).key()); i--) {
    q.rotate(1);
  }
}
```

---

## **Answer 2**

### MapTest.java

```java
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value, hasKey, and size

    /**
     * Test cases for the {@code add} method.
     */
    @Test
    public void testAdd() {
        Map<String, String> mapTest = this.createFromArgsTest();
        Map<String, String> mapRef = this.createFromArgsRef();

        mapTest.add("key1", "value1");
        mapRef.add("key1", "value1");
        assertEquals(mapRef, mapTest);

        mapTest.add("key2", "value2");
        mapRef.add("key2", "value2");
        assertEquals(mapRef, mapTest);

        mapTest.add("key3", "value3");
        mapRef.add("key3", "value3");
        assertEquals(mapRef, mapTest);
    }

    /**
     * Test cases for the {@code remove} method.
     */
    @Test
    public void testRemove() {
        Map<String, String> mapTest = this.createFromArgsTest("key1", "value1", "key2", "value2");
        Map<String, String> mapRef = this.createFromArgsRef("key1", "value1", "key2", "value2");

        assertEquals(mapRef.remove("key1"), mapTest.remove("key1"));
        assertEquals(mapRef, mapTest);

        assertEquals(mapRef.remove("key2"), mapTest.remove("key2"));
        assertEquals(mapRef, mapTest);
    }

    /**
     * Test cases for the {@code removeAny} method.
     */
    @Test
    public void testRemoveAny() {
        Map<String, String> mapTest = this.createFromArgsTest("key1", "value1", "key2", "value2");
        Map<String, String> mapRef = this.createFromArgsRef("key1", "value1", "key2", "value2");

        Map.Pair<String, String> pairTest = mapTest.removeAny();
        assertEquals(true, mapRef.hasKey(pairTest.key()))

        Map.Pair<String, String> pairRef = mapRef.remove(pairTest.key());

        assertEquals(pairRef, pairTest);
        assertEquals(mapRef.size(), mapTest.size());
    }

    /**
     * Test cases for the {@code value} method.
     */
    @Test
    public void testValue() {
        Map<String, String> mapTest = this.createFromArgsTest("key1", "value1", "key2", "value2");
        Map<String, String> mapRef = this.createFromArgsRef("key1", "value1", "key2", "value2");

        assertEquals(mapRef.value("key1"), mapTest.value("key1"));
        assertEquals(mapRef.value("key2"), mapTest.value("key2"));
    }

    /**
     * Test cases for the {@code hasKey} method.
     */
    @Test
    public void testHasKey() {
        Map<String, String> mapTest = this.createFromArgsTest("key1", "value1", "key2", "value2");
        Map<String, String> mapRef = this.createFromArgsRef("key1", "value1", "key2", "value2");

        assertEquals(mapRef.hasKey("key1"), mapTest.hasKey("key1"));
        assertEquals(mapRef.hasKey("key2"), mapTest.hasKey("key2"));
        assertEquals(mapRef.hasKey("key3"), mapTest.hasKey("key3"));
    }

    /**
     * Test cases for the {@code size} method.
     */
    @Test
    public void testSize() {
        Map<String, String> mapTest = this.createFromArgsTest("key1", "value1", "key2", "value2");
        Map<String, String> mapRef = this.createFromArgsRef("key1", "value1", "key2", "value2");

        assertEquals(mapRef.size(), mapTest.size());

        mapTest.remove("key1");
        mapRef.remove("key1");

        assertEquals(mapRef.size(), mapTest.size());
    }
}
```

---

## **Answer 3**

| Statement                                     | Variable Values                                                                 |
| --------------------------------------------- | ------------------------------------------------------------------------------- |
| `Map<String, Integer> m = new Map1L<>();`     | `m = {}`                                                                        |
| `m.add("one", 1);`                            | `m = {("one", 1)}`                                                              |
| `m.add("zero", 0);`                           | `m = {("one", 1), ("zero", 0)}`                                                 |
| `m.add("negative one", -1);`                  | `m = {("one", 1), ("zero", 0), ("negative one", -1)}`                           |
| `Pair<String, Integer> p = m.remove("zero");` | `m = {("one", 1), ("negative one", -1)}` <br> `p = ("zero", 0)`                 |
| `m.remove("one");`                            | `m = {("negative one", -1)}` <br> `p = ("zero", 0)`                             |
| `m.add("cipher", p.value());`                 | `m = {("negative one", -1), ("cipher", 0)}` <br> `p = ("zero", 0)`              |
| `m.add(p.key(), p.value());`                  | `m = {("negative one", -1), ("cipher", 0), ("zero", 0)}` <br> `p = ("zero", 0)` |
| `m.remove("negative one");`                   | `m = {("cipher", 0), ("zero", 0)}` <br> `p = ("zero", 0)`                       |
| `m.remove("cipher");`                         | `m = {("zero", 0)}` <br> `p = ("zero", 0)`                                      |
| `p = m.removeAny();`                          | `m = {}` <br> `p = ("zero", 0)`                                                 |
