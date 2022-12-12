package tokenizer;

import token.Token;

import java.text.ParseException;
import java.util.List;

public abstract class TokenizerState {

    protected Tokenizer tokenizer;

    public TokenizerState(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract void handle(char c) throws ParseException;

    protected boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    public abstract void end();
}
