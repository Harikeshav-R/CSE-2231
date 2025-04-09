## Homework 34 - The Java Collections Framework

**1. Recording the names of all employees and selecting one at random**

**OSU CSE Component:** `Sequence<String>`  
**Explanation:** A `Sequence` maintains the order of insertion and allows access by position, which is useful for retrieving a random employee. When selecting a random employee, a random index within the bounds of the sequence can be generated, and the employee at that position can be accessed directly.

**Java Collections Framework Interface:** `List<String>`  
**Explanation:** Similar to `Sequence`, `List` preserves insertion order and supports indexed access, making it easy to retrieve a random employee by generating a random index and calling `get(index)`.

---

**2. Preparing a list of unique first names**

**OSU CSE Component:** `Set<String>`  
**Explanation:** A `Set` inherently prevents duplicates, which ensures that each first name appears only once. Each employee’s first name can be extracted and added to the set; duplicates will be ignored automatically.

**Java Collections Framework Interface:** `Set<String>`  
**Explanation:** A Java `Set`, like `HashSet`, also ensures uniqueness. First names are added using `add(name)`, and duplicate names are discarded, just like in the OSU CSE `Set`.

---

**3. Counting the number of employees with each first name**

**OSU CSE Component:** `Map<String, Integer>`  
**Explanation:** A `Map` can be used to associate each first name (key) with its frequency (value). As each employee is processed, the map is updated: if the name already exists, its count is incremented; otherwise, it is added with a count of one.

**Java Collections Framework Interface:** `Map<String, Integer>`  
**Explanation:** A Java `Map`, such as `HashMap`, functions similarly. The `put` and `get` methods are used to check and update the count for each first name. If the name is present, the value is incremented; if not, it is added with a value of one.

---

**4. Creating a waiting list for lacrosse tickets**

**OSU CSE Component:** `Queue<String>`  
**Explanation:** A `Queue` enforces a first-in, first-out (FIFO) order, which suits the requirement for a waiting list. Employees join at the end and are served from the front.

**Java Collections Framework Interface:** `Queue<String>`  
**Explanation:** The Java `Queue` interface (e.g., implemented by `LinkedList`) offers `add` to enqueue and `remove` or `poll` to dequeue, mimicking the OSU `Queue`'s behavior for maintaining a fair order of ticket distribution.
