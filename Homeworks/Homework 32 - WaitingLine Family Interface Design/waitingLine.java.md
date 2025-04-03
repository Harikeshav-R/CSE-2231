## waitingLine.java

```java
/**

*

* {@code WaitingLineKernel} enhanced with secondary methods.

* @param <T> type of {@code WaitingLine} entries

* @mathdefinitions

* IS_TOTAL_PREORDER (r: binary relation on T) : boolean is

* for all x, y, z: T

* ((r(x, y) or r(y, x)) and

* (if (r(x, y) and r(y, z)) then r(x, z)))

*

* IS_SORTED (s: string of T, r: binary relation on T) : boolean is

* for all x, y: T where (<x, y> is substring of s) (r(x, y))

*/
public interface WaitingLine<T> extends WaitingLineKernel<T> {

	/**

	*

	* Find the position of the {@code entry} in {@code this}.

	* @param entry the entry being looked for

	* @return the position of the {@code entry} in {@code this}

	* @requires {@code this /= <>}

	* @ensures {@code position = position of customer in this}

	*/
	int findThePosition(T entry);
	
	/**

	* Replaces the entry in {@code this} at position {@code pos} with {@code x},

	* and returns the old entry.

	*

	* @param pos the position to replace

	* @param x the new entry at position {@code pos}

	* @return the old entry at position {@code pos}

	* @aliases reference {@code x}

	* @updates this

	* @clear x

	* @requires {@code this /= <>, 0 <= pos and pos < |this|}

	* @ensures {@code this = #this[0, pos) * <x> * #this[pos+1, |#this|) and

	* <replaceEntry> = #this[pos, pos+1)}
	*/
	T replaceEntry(int pos, T x);
}
```
