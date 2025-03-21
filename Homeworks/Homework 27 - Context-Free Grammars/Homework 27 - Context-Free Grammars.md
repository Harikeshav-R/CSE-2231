# Homework 27 - Context-Free Grammars

### Question 1

signed-real-const → sign real-const | real-const <br>
sign → + | - <br>
real-const → digit-seq . digit-seq | digit-seq . digit-seq exponent | digit-seq . | digit-seq . exponent <br>
exponent → E digit-seq | E + digit-seq | E - digit-seq <br>
digit-seq → digit digit-seq | digit <br>
digit → 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 <br>

---

### Question 2

(bool-exp OR bool-exp) <br>
⇒ (bool-exp OR F) <br>
⇒ (NOT (bool-exp) OR F) <br>
⇒ (NOT (bool-exp AND bool-exp) OR F) <br>
⇒ (NOT (F AND T) OR F) <br>

![Answer 2](image1.png "Answer 2")

---

### Question 3

![Answer 3](image2.png "Answer 3")

---

### Question 4

![Answer 4](image3.png "Answer 4")

---

### Question 5

![Answer 5](image4.png "Answer 5")

---
