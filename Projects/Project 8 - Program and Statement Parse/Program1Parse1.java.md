## Program1Parse1.java

```java
import components.map.Map;
import components.map.Map.Pair;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Hari Rameshkumar, Praj Sachan
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens, Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION")
                : "" + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        String[] primitives = { "move", "turnleft", "turnright", "infect", "skip" };

        String instruction = tokens.dequeue();
        Reporter.assertElseFatalError(instruction.equals("INSTRUCTION"),
                "Error: Keyword" + "\"" + "INSTRUCTION" + "\"" + "expected, found: "
                        + "\"" + instruction + "\"");

        String name = tokens.dequeue();
        for (String element : primitives) {
            Reporter.assertElseFatalError(!element.equals(name),
                    "Error: New instruction name must not be name "
                            + "of primitive instruction " + "\"" + element + "\"");

        }

        String is = tokens.dequeue();
        Reporter.assertElseFatalError(is.equals("IS"), "Error: Keyword" + "\"" + "IS"
                + "\"" + "expected, found: " + "\"" + is + "\"");

        body.parseBlock(tokens);

        String end = tokens.dequeue();
        Reporter.assertElseFatalError(end.equals("END"), "Error: Keyword " + "\"" + "END"
                + "\"" + "expected, found: " + "\"" + end + "\"");

        String endInstruction = tokens.dequeue();

        Reporter.assertElseFatalError(name.equals(endInstruction),
                "Error: IDENTIFIER" + "\"" + name + "\"" + "at end of instruction" + "\""
                        + endInstruction + "\"" + "must equal instruction name");

        return name;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        // TODO - fill in body

        String programToken = tokens.dequeue();

        // 1st token should be "PROGRAM"
        Reporter.assertElseFatalError(programToken.equals("PROGRAM"),
                "Error: Keyword \"PROGRAM\" expected, found: \"" + programToken + "\"");

        String programIdentifier = tokens.dequeue();
        String is = tokens.dequeue();

        // Check to for "IS"
        Reporter.assertElseFatalError(is.equals("IS"),
                "Error: Keyword \"IS\" expected, found: \"" + is + "\"");

        //Contains the instructions
        Map<String, Statement> context = this.newContext();

        //Could either be INSTRUCTION or BEGIN
        String frontToken = tokens.front();

        while (frontToken.equals("INSTRUCTION")) {
            Statement body = this.newBody();
            String instructionName = parseInstruction(tokens, body);
            for (Pair<String, Statement> contextPair : context) {

                Reporter.assertElseFatalError(!contextPair.key().equals(instructionName),
                        "Error: Instruction \"" + instructionName
                                + "\" cannot be already defined");

            }
            context.add(instructionName, body);
            frontToken = tokens.front();
        }

        Reporter.assertElseFatalError(frontToken.equals("BEGIN"),
                "Error: Keyword \"BEGIN\" expected, found: \"" + frontToken + "\"");

        frontToken = tokens.dequeue();
        Statement programBody = this.newBody();
        programBody.parseBlock(tokens);

        String endToken = tokens.dequeue();

        //Check for "END"
        Reporter.assertElseFatalError(endToken.equals("END"),
                "Error: Keyword \"END\" expected, found: \"" + endToken + "\"");

        String endProgramIdentifier = tokens.dequeue();
        // ID Has to equal
        Reporter.assertElseFatalError(endProgramIdentifier.equals(programIdentifier),
                "Error: IDENTIFIER \"" + endProgramIdentifier
                        + "\" at end of instruction \"" + programIdentifier
                        + "\" must equal instruction name");

        //Checks for end of program.
        Reporter.assertElseFatalError(tokens.front().equals("### END OF INPUT ###"),
                "Error: END-OF-INPUT expected, found: " + "\"" + tokens.front() + "\"");

        this.setName(programIdentifier);
        this.swapBody(programBody);
        this.swapContext(context);

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}

```
