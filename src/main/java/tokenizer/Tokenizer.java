package tokenizer;

import token.Token;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String expression;
    private int cursor;
    private List<Token> tokenList;
    private TokenizerState state;

    public Tokenizer(String expression) {
        this.expression = expression;
    }

    public List<Token> parse() throws ParseException {
        this.tokenList = new ArrayList<>();
        this.state = new StartState(this);
        for (this.cursor = 0; this.cursor < this.expression.length(); this.cursor++) {
            state.handle(this.expression.charAt(this.cursor));
        }
        state.end();
        return tokenList;
    }

    protected int getCursor() {
        return cursor;
    }

    protected void addToken(Token token) {
        tokenList.add(token);
    }

    protected void setState(TokenizerState newState) {
        this.state = newState;
    }
}
