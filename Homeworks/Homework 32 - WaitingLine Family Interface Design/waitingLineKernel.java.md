## waitingLineKernel.java

```java
import components.standard.Standard;

/**

*

* First-in-first-out (FIFO) waiting line component with primary methods.

* @param <T> type of {@code WaitingLineKernel} entries

* @mathmodel type WaitingLineKernel is modeled by string of T

* @initially ensures this = <>

* @iterator ~this.seen * ~this.unseen = this

*/
public interface WaitingLineKernel<T> extends Standard<WaitingLine<T>>, Iterable<T> {
	/**

	*

	* Adds {@code x} to the end of {@code this} if {@code this} does not contain {@code x}.

	* @param customer the entry to be added

	* @aliases reference {@code x}

	* @updates {@code this}

	* @requires {@code this does not contain x}

	* @ensures {@code this = #this * <x>}

	*/
	void addLine(T customer);

	/**

	*

	* Removes {@code customer} from the front of {@code this}.

	* @return the entry removed

	* @updates {@code this}

	* @requires {@code this /= <>}

	* @ensures {@code #this = <removeFrontFromLine> * this}

	*/
	T removeFrontLine();

	/**

	*

	* Reports the front of {@code this}.

	* @return the front entry of {@code this}

	* @aliases reference returned by {@code front}

	* @requires {@code this /= <>}

	* @ensures {@code <front> is prefix of this}

	*/
	T frontLine();

	/**}

	* Reports length of {@code this}.

	*

	* @return the length of {@code this}

	* @ensures {@code length = |this|}

	*/
	int lengthOfLine();

}
```
