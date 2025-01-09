# Homework 3

The code below contains the reimplementation of the smooth function that now returns the new smoothed sequence instead of replacing a parameter.
It also contains two distinct implementations of the new smooth method, one of which uses recursion, while the other uses iteration.

```java
import components.sequence.Sequence;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 *
 * @author Harikeshav Rameshkumar
 *
 */
public final class SequenceSmooth {

  /**
   * Private constructor so this utility class cannot be instantiated.
   */
  private SequenceSmooth() {
  }

  /**
   * Smooths a given {@code Sequence<Integer>}.
   *
   * @param sequence
   *                 the sequence to smooth
   * @return outputSequence
   *                 the resulting sequence
   * @requires |s1| >= 1
   * @ensures
   *
   *          <pre>
   * |s2| = |s1| - 1  and
   *  for all i, j: integer, a, b: string of integer
   *      where (s1 = a * <i> * <j> * b)
   *    (there exists c, d: string of integer
   *       (|c| = |a|  and
   *        s2 = c * <(i+j)/2> * d))
   *          </pre>
   */
  public static Sequence<Integer> smooth(Sequence<Integer> sequence) {
    assert sequence != null : "Violation of: s1 is not null";
    assert sequence.length() >= 1 : "Violation of: |s1| >= 1";

    Sequence<Integer> outputSequence = sequence.newInstance();

    smoothRecursiveHelper(sequence, outputSequence, 0);
    // smoothIterativeHelper(sequence, outputSequence);

    return outputSequence;
  }

  /**
   * Recursive implementation for smoothing a given {@code Sequence<Integer>}.
   *
   * @param s1
   *              the sequence to smooth
   * @param s2
   *              the resulting sequence
   * @param index
   *              the current index in s1 to process
   * @replaces s2
   * @requires |s1| >= 1
   * @ensures
   *
   *          <pre>
   * |s2| = |s1| - 1  and
   *  for all i, j: integer, a, b: string of integer
   *      where (s1 = a * <i> * <j> * b)
   *    (there exists c, d: string of integer
   *       (|c| = |a|  and
   *        s2 = c * <(i+j)/2> * d))
   *          </pre>
   */
  private static void smoothRecursiveHelper(Sequence<Integer> s1, Sequence<Integer> s2,
      int index) {
    if (index >= s1.length() - 1) {
      return;
    }

    int avg = (s1.entry(index) + s1.entry(index + 1)) / 2;
    s2.add(s2.length(), avg);
    smoothRecursiveHelper(s1, s2, index + 1);
  }

  /**
   * Iterative implementation for smoothing a given {@code Sequence<Integer>}.
   *
   * @param s1
   *           the sequence to smooth
   * @param s2
   *           the resulting sequence
   * @replaces s2
   * @requires |s1| >= 1
   * @ensures
   *
   *          <pre>
   * |s2| = |s1| - 1  and
   *  for all i, j: integer, a, b: string of integer
   *      where (s1 = a * <i> * <j> * b)
   *    (there exists c, d: string of integer
   *       (|c| = |a|  and
   *        s2 = c * <(i+j)/2> * d))
   *          </pre>
   */
  private static void smoothIterativeHelper(Sequence<Integer> s1,
      Sequence<Integer> s2) {
    for (int i = 0; i < s1.length() - 1; i++) {
      int avg = (s1.entry(i) + s1.entry(i + 1)) / 2;
      s2.add(s2.length(), avg);
    }
  }
}
```
