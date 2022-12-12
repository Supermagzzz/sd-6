package token;

import visitor.TokenVisitor;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public class NumberToken implements Token {

    private long value;

    public NumberToken(long value) {
        this.value = value;
    }

    public void accept(TokenVisitor visitor) throws IOException, ParseException {
        visitor.visit(this);
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberToken that = (NumberToken) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
