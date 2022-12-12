package token;

import visitor.TokenVisitor;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public class Brace implements Token {
    public enum Type {
        LEFT, RIGHT
    }

    private Type type;

    public Brace(Type type) {
        this.type = type;
    }

    public void accept(TokenVisitor visitor) throws IOException, ParseException {
        visitor.visit(this);
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brace brace = (Brace) o;
        return type == brace.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
