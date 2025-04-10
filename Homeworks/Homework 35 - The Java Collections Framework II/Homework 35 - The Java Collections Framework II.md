## Homework 35 - The Java Collections Framework II

### Question 1

```java
/**
  * Raises the salary of all the employees in {@code map} whose name starts
  * with the given {@code initial} by the given {@code raisePercent}.
  *
  * @param map
  *            the name to salary map
  * @param initial
  *            the initial of names of employees to be given a raise
  * @param raisePercent
  *            the raise to be given as a percentage of the current salary
  * @updates map
  * @requires [the salaries in map are positive]  and  raisePercent > 0
  * @ensures <pre>
  * DOMAIN(map) = DOMAIN(#map)  and
  * [the salaries of the employees in map whose names start with the given
  *  initial have been increased by raisePercent percent (and truncated to
  *  the nearest integer); all other employees have the same salary]
  * </pre>
  */
private static void giveRaise(components.map.Map<String, Integer> map,
        char initial, int raisePercent) {
    Map<String, Integer> tempMap = map.newInstance();

    for (int i = 0; i < map.size(); i++) {
        Map.Pair<String, Integer> pair = map.removeAny();
        String name = pair.key();
        int salary = pair.value();

        if (name.charAt(0) == initial) {
            salary = salary + (salary * raisePercent) / 100;
        }

        tempMap.add(name, salary);
    }

    map.transferFrom(tempMap);
}
```

---

### Question 2

```java
/**
  * Raises the salary of all the employees in {@code map} whose name starts
  * with the given {@code initial} by the given {@code raisePercent}.
  *
  * @param map
  *            the name to salary map
  * @param initial
  *            the initial of names of employees to be given a raise
  * @param raisePercent
  *            the raise to be given as a percentage of the current salary
  * @updates map
  * @requires <pre>
  * [the salaries in map are positive]  and  raisePercent > 0  and
  * [the dynamic types of map and of all objects reachable from map
  *  (including any objects returned by operations (such as entrySet() and,
  *  from there, iterator()), and so on, recursively) support all
  *  optional operations]
  * </pre>
  * @ensures <pre>
  * DOMAIN(map) = DOMAIN(#map)  and
  * [the salaries of the employees in map whose names start with the given
  *  initial have been increased by raisePercent percent (and truncated to
  *  the nearest integer); all other employees have the same salary]
  * </pre>
  */
private static void giveRaise(java.util.Map<String, Integer> map,
        char initial, int raisePercent) {
    for (java.util.Map.Entry<String, Integer> entry : map.entrySet()) {
        String name = entry.getKey();
        int salary = entry.getValue();
        if (name.charAt(0) == initial) {
            int raisedSalary = salary + (salary * raisePercent) / 100;
            entry.setValue(raisedSalary);
        }
    }
}
```
