package tokenizer;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.text.ParseException;

public class NumberState extends TokenizerState {
    private long result;

    public NumberState(Tokenizer tokenizer) {
        super(tokenizer);
        this.result = 0;
    }

    public void handle(char c) throws ParseException {
        if (isNumber(c)) {
            result = result * 10 + (c - '0');
        } else {
            tokenizer.addToken(new NumberToken(result));
            TokenizerState nextState = new StartState(tokenizer);
            tokenizer.setState(nextState);
            nextState.handle(c);
        }
    }

    public void end() {
        tokenizer.addToken(new NumberToken(result));
    }
}
