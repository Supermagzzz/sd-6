package tokenizer;

import token.Brace;
import token.Operation;
import token.Token;

import java.text.ParseException;
import java.util.List;

public class StartState extends TokenizerState {
    public StartState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    public void handle(char c) throws ParseException {
        if (Character.isWhitespace(c)) {
            return;
        }

        if (isNumber(c)) {
            TokenizerState newState = new NumberState(tokenizer);
            tokenizer.setState(newState);
            newState.handle(c);
            return;
        }

        Token nextToken;
        if (c == '(') {
            nextToken = new Brace(Brace.Type.LEFT);
        } else if (c == ')') {
            nextToken = new Brace(Brace.Type.RIGHT);
        } else if (c == '+') {
            nextToken = new Operation(Operation.Type.PLUS);
        } else if (c == '-') {
            nextToken = new Operation(Operation.Type.MINUS);
        } else if (c == '*') {
            nextToken = new Operation(Operation.Type.MULTIPLY);
        } else if (c == '/') {
            nextToken = new Operation(Operation.Type.DIVISION);
        } else {
            throw new ParseException("Unexpected char: " + c, tokenizer.getCursor());
        }
        tokenizer.addToken(nextToken);
    }

    public void end() {
        // do nothing
    }
}
