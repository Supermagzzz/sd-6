package token;

import visitor.TokenVisitor;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public class Operation implements Token {
    public enum Type {
        PLUS, MINUS, DIVISION, MULTIPLY
    }

    private Type type;

    public Operation(Type type) {
        this.type = type;
    }

    public void accept(TokenVisitor visitor) throws IOException, ParseException {
        visitor.visit(this);
    }

    public Type getType() {
        return type;
    }

    public int getPriority() {
        if (getType() == Type.PLUS || getType() == Type.MINUS) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return type == operation.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
