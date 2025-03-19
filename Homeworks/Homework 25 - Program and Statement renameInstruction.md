# Homework 25 - Program and Statement renameInstruction

### Question 1

```java
public static void renameInstruction(Statement s, String oldName,
        String newName) {
    switch (s.kind()) {
        case BLOCK: {
            int length = s.lengthOfBlock();
            for (int i = 0; i < length; i++) {
                Statement subTree = s.removeFromBlock(i);
                renameInstruction(subTree, oldName, newName);
                s.addToBlock(i, subTree);
            }
            break;
        }
        case IF: {
            Statement subTree = s.newInstance();
            Condition ifCondition = s.disassembleIf(subTree);
            renameInstruction(subTree, oldName, newName);
            s.assembleIf(ifCondition, subTree);
        }
        case IF_ELSE: {

            Statement subTreeIf = s.newInstance();
            Statement subTreeElse = s.newInstance();
            Condition ifElseCondition = s.disassembleIfElse(subTreeIf,
                    subTreeElse);
            renameInstruction(subTreeIf, oldName, newName);
            renameInstruction(subTreeElse, oldName, newName);
            s.assembleIfElse(ifElseCondition, subTreeIf, subTreeElse);

        }
        case WHILE: {

            Statement subTree = s.newInstance();
            Condition whileCondition = s.disassembleWhile(subTree);
            renameInstruction(subTree, oldName, newName);
            s.assembleWhile(whileCondition, subTree);

        }
        case CALL: {
            String call = s.disassembleCall();
            if (call.equals(oldName)) {
                s.assembleCall(newName);
            } else {
                s.assembleCall(call);
            }
        }
        default:
            break;
    }

}
```

---

### Question 2

```java
public static void renameInstruction(Program p, String oldName,
        String newName) {
    Map<String, Statement> c = p.newContext();
    Map<String, Statement> ctxt = p.newContext();
    p.swapContext(ctxt);
    while (ctxt.size() > 0) {
        Map.Pair<String, Statement> instr = ctxt.removeAny();
        String key = instr.key();
        if (instr.key().equals(oldName)) {
            key = newName;
        }
        renameInstruction(instr.value(), oldName, newName);
        c.add(key, instr.value());
    }

    p.swapContext(c);
    Statement pBody = p.newBody();
    p.swapBody(pBody);
    renameInstruction(pBody, oldName, newName);
    p.replaceBody(pBody);
}
```
