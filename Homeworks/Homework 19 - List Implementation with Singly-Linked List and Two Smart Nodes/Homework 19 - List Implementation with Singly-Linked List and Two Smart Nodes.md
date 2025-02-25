# Homework 19 - List Implementation with Singly-Linked List and Two Smart Nodes

## **Answer 1**

| Statement                              | Variable Values                           |
| -------------------------------------- | ----------------------------------------- |
| `List<Integer> list = new List1L<>();` | `list = (<>, <>)`                         |
| `list.addRightFront(1)`                | `list = (<>, <1>)`                        |
| `list.addRightFront(0)`                | `list = (<>, <0, 1>)`                     |
| `list.addRightFront(2)`                | `list = (<>, <2, 0, 1>)`                  |
| `list.advance()`                       | `list = (<2>, <0, 1>)`                    |
| `list.addRightFront(-1)`               | `list = (<2>, <-1, 0, 1>)`                |
| `list.advance()`                       | `list = (<2, -1>, <0, 1>)`                |
| `list.moveToFinish()`                  | `list = (<2, -1, 0, 1>, <>)`              |
| `list.retreat()`                       | `list = (<2, -1, 0>, <1>)`                |
| `int i = list.rightFront();`           | `list = (<2, -1, 0>, <1>)` <br> `i = 1`   |
| `list.moveToFront();`                  | `list = (<>, <2, -1, 0, 1>)` <br> `i = 1` |

## **Anwer 2**

```java
/**
* Retreats the position in {@code this} by one.
*
* @updates this
* @requires this.left /= <>
* @ensures <pre>
* this.left * this.right = #this.left * #this.right  and
* |this.left| = |#this.left| - 1
* </pre>
*/
public void retreat() {
    int position = leftLength() - 1;
    moveToStart();

    while (leftLength() != i) {
        advance();
    }
}
```
