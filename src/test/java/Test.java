import token.NumberToken;
import token.Operation;
import token.Token;
import tokenizer.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParseVisitor;
import visitor.PrintVisitor;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

public class Test {

    void tryApp(String expr, List<Token> expected, Long answer) throws IOException, ParseException {
        Tokenizer tokenizer = new Tokenizer(expr);
        List<Token> tokens = tokenizer.parse();

        ParseVisitor parseVisitor = new ParseVisitor();
        for (Token token : tokens) {
            token.accept(parseVisitor);
        }
        List<Token> polish = parseVisitor.end();

        CalcVisitor calcVisitor = new CalcVisitor();

        for (Token token : polish) {
            token.accept(calcVisitor);
        }

        assertEquals(polish, expected);
        assertEquals(calcVisitor.end(), answer);
    }

    @org.junit.Test
    public void twoPlusTwo() throws IOException, ParseException {
        tryApp(
                "2 + 2",
                List.of(new NumberToken(2), new NumberToken(2), new Operation(Operation.Type.PLUS)),
                4L
        );
    }

    @org.junit.Test
    public void priorityTest() throws IOException, ParseException {
        tryApp(
                "2 + 3 / 1",
                List.of(
                        new NumberToken(2),
                        new NumberToken(3),
                        new NumberToken(1),
                        new Operation(Operation.Type.DIVISION),
                        new Operation(Operation.Type.PLUS)
                ),
                5L
        );
    }

    @org.junit.Test
    public void allOperations() throws IOException, ParseException {
        tryApp(
                "1 + 2 * 4 - 5 / 2",
                List.of(
                        new NumberToken(1),
                        new NumberToken(2),
                        new NumberToken(4),
                        new Operation(Operation.Type.MULTIPLY),
                        new Operation(Operation.Type.PLUS),
                        new NumberToken(5),
                        new NumberToken(2),
                        new Operation(Operation.Type.DIVISION),
                        new Operation(Operation.Type.MINUS)
                ),
                7L
        );
    }

    @org.junit.Test
    public void veryHard() throws IOException, ParseException {
        tryApp(
                "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2",
                List.of(
                        new NumberToken(23),
                        new NumberToken(10),
                        new Operation(Operation.Type.PLUS),
                        new NumberToken(5),
                        new Operation(Operation.Type.MULTIPLY),
                        new NumberToken(3),
                        new NumberToken(32),
                        new NumberToken(5),
                        new Operation(Operation.Type.PLUS),
                        new Operation(Operation.Type.MULTIPLY),
                        new NumberToken(10),
                        new NumberToken(4),
                        new NumberToken(5),
                        new Operation(Operation.Type.MULTIPLY),
                        new Operation(Operation.Type.MINUS),
                        new Operation(Operation.Type.MULTIPLY),
                        new Operation(Operation.Type.MINUS),
                        new NumberToken(8),
                        new NumberToken(2),
                        new Operation(Operation.Type.DIVISION),
                        new Operation(Operation.Type.PLUS)
                ),
                1279L
        );
    }

    @org.junit.Test
    public void throwTwoNumbers() {
        assertThrows(ParseException.class, () -> {
                    tryApp(
                            " 1 1",
                            List.of(),
                            0L
                    );
                }
        );
    }

    @org.junit.Test
    public void throwUnary() {
        assertThrows(ParseException.class, () -> {
                    tryApp(
                            "- 1",
                            List.of(),
                            0L
                    );
                }
        );
    }

    @org.junit.Test
    public void throwRuntime() {
        assertThrows(RuntimeException.class, () -> {
                    tryApp(
                            "1 / 0",
                            List.of(),
                            0L
                    );
                }
        );
    }

    @org.junit.Test
    public void twoPlusTwoWhiteSpaces() throws IOException, ParseException {
        tryApp(
                "       2       +        2        ",
                List.of(new NumberToken(2), new NumberToken(2), new Operation(Operation.Type.PLUS)),
                4L
        );
    }
}