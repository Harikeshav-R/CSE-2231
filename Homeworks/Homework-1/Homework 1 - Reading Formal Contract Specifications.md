1. **Suppose `seq1 = <2, 4, 6>` and `seq2 = <-5, 12>`. What are the values of `seq1` and `seq2` after the call `smooth(seq1, seq2)`?**

   - **ANswer**:
     - `seq1` remains unchanged: `<2, 4, 6>`.
     - `seq2` becomes `<3, 5>`.
       <!-- - **Explanation**: According to the specification, `smooth` takes a sequence `s1` and produces a new sequence `s2` with one fewer element. Each element in `s2` is computed as the average of adjacent elements in `s1`. For this example: -->
       <!--   - \( (2 + 4)/2 = 3 \) -->
       <!--   - \( (4 + 6)/2 = 5 \) -->
       <!---->

2. **Suppose `seq1 = <7>` and `seq2 = <13, 17, 11>`. What are the values of `seq1` and `seq2` after the call `smooth(seq1, seq2)`?**

   - **Answer**:
     - `seq1` remains unchanged: `<7>`.
     - `seq2` becomes empty: `<>`.
     <!-- - **Explanation**: The `requires` clause states that `|s1| >= 1`. Since `|s1| = 1`, the resulting sequence `s2` will have \( |s2| = |s1| - 1 = 0 \), i.e., `s2` will be empty. -->

3. **Suppose `seq1 = <>` and `seq2 = <>`. What are the values of `seq1` and `seq2` after the call `smooth(seq1, seq2)`?**

   - **Answer**:
     - The call to `smooth(seq1, seq2)` is invalid because it violates the `requires` clause.
     <!-- - **Explanation**: The `requires` clause specifies that `|s1| >= 1`. If `s1` is empty, the precondition is not met, and the method cannot be called. -->

4. **Explain informally, but precisely, what the specs of `smooth` say about the method's behavior.**

   - **Answer**:
     - The method `smooth` takes two sequences, `s1` and `s2`. It replaces the contents of `s2` with a sequence derived from `s1`. The resulting sequence `s2` has one fewer element than `s1`. Each element in `s2` is calculated as the average of two consecutive elements in `s1`. For example, if `s1 = <a, b, c>`, then `s2 = <(a+b)/2, (b+c)/2>`.
     - The original sequence `s1` remains unchanged after the operation. If `s1` has only one element, `s2` will be empty. If `s1` is empty, the method cannot be called as it would violate the `requires` clause.
