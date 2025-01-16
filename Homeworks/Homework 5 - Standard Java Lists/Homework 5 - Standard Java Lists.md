# Homework 5

### **Answer 3:**

| Statement                                              | Variable Values             |
| ------------------------------------------------------ | --------------------------- |
| `List<Integer> list = new SomeListImplementation<>();` | list = []                   |
| `list.add(7);`                                         | list = [7]                  |
| `list.add(-12);`                                       | list = [7, -12]             |
| `list.add(3);`                                         | list = [7, -12, 3]          |
| `int x = list.size();`                                 | list = [7, -12, 3], x = 3   |
| `x = list.get(1);`                                     | list = [7, -12, 3], x = -12 |
| `x = list.remove(0);`                                  | list = [-12, 3], x = 7      |
| `x = list.remove(1);`                                  | list = [-12], x = 3         |
| `x = list.size();`                                     | list = [-12], x = 1         |

### **Answer 4:**

1. **Benefits vs. Pitfalls of Optional Operations in `java.util.List`:**

   **Benefits:**

   - By marking `add(E e)` and `remove(int index)` as optional, Java allows for list implementations that may not support all operations, such as immutable lists, read-only lists, or custom lists designed for specific constraints (e.g., fixed-size lists).
   - Some list implementations can optimize performance by avoiding the overhead of supporting operations that are not relevant to their intended use case. For instance, an immutable list doesn't need to implement logic for modifying elements.
   - This approach enables a single interface (`List`) to accommodate a wide variety of list-like data structures without requiring them all to support every operation.

   **Pitfalls:**

   - Attempting to invoke optional operations on unsupported implementations can lead to `UnsupportedOperationException`, which only reveals itself at runtime.
   - Developers might incorrectly assume that all list implementations support modification operations, leading to unexpected behavior in code.

2. **Benefits vs. Pitfalls of Element Restrictions in `java.util.List`:**

   **Benefits:**

   - By allowing restrictions on element types or prohibiting `null`, list implementations can enforce constraints that align with their intended use.
   - Restrictions such as prohibiting `null` elements can simplify internal logic and reduce the need for `null` checks, improving performance.
   - Prohibiting certain elements can prevent logical errors, such as storing unintended or incompatible types.

   **Pitfalls:**

   - Lists with restrictions are less versatile and may require additional conversion or validation steps when integrating with other components.
   - Understanding and working within the constraints of restricted lists requires familiarity with the specific implementation's requirements, which might increase complexity.
   - The varying restrictions among list implementations can lead to inconsistencies, requiring handling of edge cases and testing for compatibility when switching implementations.
