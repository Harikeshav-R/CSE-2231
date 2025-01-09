import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Sample JUnit test fixture for SequenceSmooth.
 *
 * @author Harikeshav Rameshkumar
 *
 */
public final class SequenceSmoothTest {

  /**
   * Constructs and returns a sequence of the integers provided as arguments.
   *
   * @param args
   *             0 or more integer arguments
   * @return the sequence of the given arguments
   * @ensures createFromArgs= [the sequence of integers in args]
   */
  private Sequence<Integer> createFromArgs(Integer... args) {
    Sequence<Integer> s = new Sequence1L<Integer>();
    for (Integer x : args) {
      s.add(s.length(), x);
    }
    return s;
  }

  /**
   * Test smooth with s1 = <2, 4, 6> and s2 = <-5, 12>.
   */
  @Test
  public void test1() {
    /*
     * Set up variables and call method under test
     */
    Sequence<Integer> seq1 = this.createFromArgs(2, 4, 6);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(2, 4, 6);
    Sequence<Integer> seq2 = this.createFromArgs(-5, 12);
    Sequence<Integer> expectedSeq2 = this.createFromArgs(3, 5);
    SequenceSmooth.smooth(seq1, seq2);
    /*
     * Assert that values of variables match expectations
     */
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  /**
   * Test smooth with s1 = <7> and s2 = <13, 17, 11>.
   */
  @Test
  public void test2() {
    /*
     * Set up variables and call method under test
     */
    Sequence<Integer> seq1 = this.createFromArgs(7);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(7);
    Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
    Sequence<Integer> expectedSeq2 = this.createFromArgs();
    SequenceSmooth.smooth(seq1, seq2);
    /*
     * Assert that values of variables match expectations
     */
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  /**
   * Test smooth with s1 = <3, 5, 7, 9> and s2 = <0, 0, 0>.
   */
  @Test
  public void test3() {
    /*
     * Set up variables and call method under test
     */
    Sequence<Integer> seq1 = this.createFromArgs(3, 5, 7, 9);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(3, 5, 7, 9);
    Sequence<Integer> seq2 = this.createFromArgs(0, 0, 0);
    Sequence<Integer> expectedSeq2 = this.createFromArgs(4, 6, 8);
    SequenceSmooth.smooth(seq1, seq2);
    /*
     * Assert that values of variables match expectations
     */
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  /**
   * Test smooth with s1 = <1, 3> and s2 = <9, 8, 7>.
   */
  @Test
  public void test4() {
    /*
     * Set up variables and call method under test
     */
    Sequence<Integer> seq1 = this.createFromArgs(1, 3);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(1, 3);
    Sequence<Integer> seq2 = this.createFromArgs(9, 8, 7);
    Sequence<Integer> expectedSeq2 = this.createFromArgs(2);
    SequenceSmooth.smooth(seq1, seq2);
    /*
     * Assert that values of variables match expectations
     */
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  /**
   * Test smooth with s1 = <5, 5, 5, 5> and s2 = <99, 99, 99>.
   */
  @Test
  public void test5() {
    /*
     * Set up variables and call method under test
     */
    Sequence<Integer> seq1 = this.createFromArgs(5, 5, 5, 5);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(5, 5, 5, 5);
    Sequence<Integer> seq2 = this.createFromArgs(99, 99, 99);
    Sequence<Integer> expectedSeq2 = this.createFromArgs(5, 5, 5);
    SequenceSmooth.smooth(seq1, seq2);
    /*
     * Assert that values of variables match expectations
     */
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  /**
   * Test smooth with s1 = <> and s2 = <> (invalid case).
   */
  @Test
  public void test6() {
    /*
     * Set up variables and call method under test
     */
    Sequence<Integer> seq1 = this.createFromArgs();
    Sequence<Integer> seq2 = this.createFromArgs();
    SequenceSmooth.smooth(seq1, seq2);
  }

  /**
   * Test smooth with negative numbers: s1 = <-5, -10, -15> and s2 = <0>.
   */
  @Test
  public void test7() {
    Sequence<Integer> seq1 = this.createFromArgs(-5, -10, -15);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(-5, -10, -15);
    Sequence<Integer> seq2 = this.createFromArgs(0);
    Sequence<Integer> expectedSeq2 = this.createFromArgs(-7, -12);
    SequenceSmooth.smooth(seq1, seq2);
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  /**
   * Test smooth with a mix of positive and negative numbers: s1 = <10, -20,
   * 30, -40> and s2 = <-1, 2>.
   */
  @Test
  public void test8() {
    Sequence<Integer> seq1 = this.createFromArgs(10, -20, 30, -40);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(10, -20, 30, -40);
    Sequence<Integer> seq2 = this.createFromArgs(-1, 2);
    Sequence<Integer> expectedSeq2 = this.createFromArgs(-5, 5, -5);
    SequenceSmooth.smooth(seq1, seq2);
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  /**
   * Test smooth with consecutive negative numbers: s1 = <-1, -2, -3, -4> and
   * s2 = <99>.
   */
  @Test
  public void test9() {
    Sequence<Integer> seq1 = this.createFromArgs(-1, -2, -3, -4);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(-1, -2, -3, -4);
    Sequence<Integer> seq2 = this.createFromArgs(99);
    Sequence<Integer> expectedSeq2 = this.createFromArgs(-1, -2, -3);
    SequenceSmooth.smooth(seq1, seq2);
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  @Test
  public void test10() {
    Sequence<Integer> seq1 = this.createFromArgs(1073741825, 1073741825);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(1073741825, 1073741825);
    Sequence<Integer> seq2 = this.createFromArgs();
    Sequence<Integer> expectedSeq2 = this.createFromArgs(1073741825);
    SequenceSmooth.smooth(seq1, seq2);
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  @Test
  public void test11() {
    Sequence<Integer> seq1 = this.createFromArgs(1073741825, -1073741825);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(1073741825, -1073741825);
    Sequence<Integer> seq2 = this.createFromArgs();
    Sequence<Integer> expectedSeq2 = this.createFromArgs(0);
    SequenceSmooth.smooth(seq1, seq2);
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }

  @Test
  public void test12() {
    Sequence<Integer> seq1 = this.createFromArgs(-1073741823, 1073741824);
    Sequence<Integer> expectedSeq1 = this.createFromArgs(-1073741823, 1073741824);
    Sequence<Integer> seq2 = this.createFromArgs();
    Sequence<Integer> expectedSeq2 = this.createFromArgs(0);
    SequenceSmooth.smooth(seq1, seq2);
    assertEquals(expectedSeq1, seq1);
    assertEquals(expectedSeq2, seq2);
  }
}
