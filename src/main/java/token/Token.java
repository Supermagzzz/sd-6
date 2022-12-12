package token;

import visitor.TokenVisitor;

import java.io.IOException;
import java.text.ParseException;

public interface Token {
    void accept(TokenVisitor visitor) throws IOException, ParseException;
}
