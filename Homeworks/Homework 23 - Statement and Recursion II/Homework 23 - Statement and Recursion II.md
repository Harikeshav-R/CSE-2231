# Homework 23 - Statement and Recursion II

```java
/**
* Refactors the given {@code Statement} so that every IF_ELSE statement
* with a negated condition (NEXT_IS_NOT_EMPTY, NEXT_IS_NOT_ENEMY,
* NEXT_IS_NOT_FRIEND, NEXT_IS_NOT_WALL) is replaced by an equivalent
* IF_ELSE with the opposite condition and the "then" and "else" BLOCKs
* switched. Every other statement is left unmodified.
*
* @param s
*            the {@code Statement}
* @updates s
* @ensures
*
*          <pre>
* s = [#s refactored so that IF_ELSE statements with "not"
*   conditions are simplified so the "not" is removed]
*          </pre>
*/
public static void simplifyIfElse(Statement s) {
    switch (s.kind()) {
        case BLOCK: {

            // TODO - fill in case
            int length = s.lengthOfBlock();
            for (int i = 0; i < length; i++) {
                Statement subLable = s.removeFromBlock(i);
                simplifyIfElse(subLable);
                s.addToBlock(i, subLable);
            }

            break;
        }
        case IF: {

            // TODO - fill in case
            Statement subLabel = s.newInstance();
            Statement.Condition condition = s.disassembleIf(subLabel);
            simplifyIfElse(subLabel);
            s.assembleIf(condition, subLabel);

            break;
        }
        case IF_ELSE: {

            // TODO - fill in case
            Statement subLabelIf = s.newInstance();
            Statement subLabelElse = s.newInstance();
            Statement.Condition condition = s.disassembleIfElse(subLabelIf,
                    subLabelElse);
            switch (condition.name()) {
                case "NEXT_IS_NOT_EMPTY": {
                    condition = condition.NEXT_IS_EMPTY;
                    simplifyIfElse(subLabelIf);
                    simplifyIfElse(subLabelElse);
                    s.assembleIfElse(condition, subLabelElse, subLabelIf);
                    break;
                }
                case "NEXT_IS_NOT_ENEMY": {
                    condition = condition.NEXT_IS_ENEMY;
                    simplifyIfElse(subLabelIf);
                    simplifyIfElse(subLabelElse);
                    s.assembleIfElse(condition, subLabelElse, subLabelIf);
                    break;

                }
                case "NEXT_IS_NOT_FRIEND": {
                    condition = condition.NEXT_IS_FRIEND;
                    simplifyIfElse(subLabelIf);
                    simplifyIfElse(subLabelElse);
                    s.assembleIfElse(condition, subLabelElse, subLabelIf);
                    break;

                }
                case "NEXT_IS_NOT_WALL": {
                    condition = condition.NEXT_IS_WALL;
                    simplifyIfElse(subLabelIf);
                    simplifyIfElse(subLabelElse);
                    s.assembleIfElse(condition, subLabelElse, subLabelIf);
                    break;

                }

            }

            break;
        }
        case WHILE: {

            // TODO - fill in case
            Statement subLabel = s.newInstance();
            Statement.Condition condition = s.disassembleWhile(subLabel);
            simplifyIfElse(subLabel);
            s.assembleWhile(condition, subLabel);

            break;
        }
        case CALL: {
            // nothing to do here...can you explain why?
            break;
        }
        default: {
            // this will never happen...can you explain why?
            break;
        }
    }

}

```
