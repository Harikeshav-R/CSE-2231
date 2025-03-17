# Homework 24 - Statement and Recursion III

```java
public void prettyPrint(SimpleWriter out, int offset) {
    assert out != null : "Violation of: out is not null";
    assert out.isOpen() : "Violation of: out.is_open";
    assert offset >= 0 : "Violation of: 0 <= offset";

    switch (this.kind()) {
        case BLOCK: {
            int length = this.lengthOfBlock();
            for (int i = 0; i < length; i++) {
                Statement subTree = this.removeFromBlock(i);
                subTree.prettyPrint(out, offset);
                this.addToBlock(i, subTree);
            }
            break;
        }
        case IF: {
            Statement subTree = this.newInstance();
            Condition ifCondition = this.disassembleIf(subTree);
            printSpaces(out, offset);

            out.println("IF " + toStringCondition(ifCondition));
            subTree.prettyPrint(out, offset + Program.INDENT_SIZE);

            for (int i = 0; i < offset; i++) {
                out.print(" ");
            }
            out.println("END IF");
            this.assembleIf(ifCondition, subTree);
            break;
        }
        case IF_ELSE: {
            Statement subTreeIf = this.newInstance();
            Statement subTreeElse = this.newInstance();
            Condition ifElseCondition = this.disassembleIfElse(subTreeIf,
                    subTreeElse);
            printSpaces(out, offset);
            out.println(
                    "IF " + toStringCondition(ifElseCondition) + " THEN");
            subTreeIf.prettyPrint(out, offset + Program.INDENT_SIZE);

            printSpaces(out, offset);
            out.println("ELSE");
            subTreeElse.prettyPrint(out, offset + Program.INDENT_SIZE);

            printSpaces(out, offset);
            out.println("END IF");
            this.assembleIfElse(ifElseCondition, subTreeIf, subTreeElse);

            break;
        }
        case WHILE: {
            Statement subTree = this.newInstance();
            Condition whileCondition = this.disassembleWhile(subTree);
            printSpaces(out, offset);
            out.println(
                    "WHILE " + toStringCondition(whileCondition) + " DO");
            subTree.prettyPrint(out, offset + Program.INDENT_SIZE);

            printSpaces(out, offset);
            out.println("END WHILE");
            this.assembleWhile(whileCondition, subTree);

            break;
        }
        case CALL: {
            String call = this.disassembleCall();
            printSpaces(out, offset);
            out.println(call);
            this.assembleCall(call);

            break;
        }
        default: {
            // this will never happen...
            break;
        }
    }
}
```
