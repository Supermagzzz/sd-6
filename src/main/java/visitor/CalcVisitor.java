package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private final Stack<Long> stack = new Stack<>();

    @Override
    public void visit(Brace brace) throws IOException {
        throw new IllegalArgumentException("Unexpected brace");
    }

    @Override
    public void visit(Operation operation) throws IOException {
        if (stack.size() < 2) {
            throw new IllegalArgumentException("Unexpected operation");
        }
        long b = stack.pop();
        long a = stack.pop();
        switch (operation.getType()) {
            case PLUS -> stack.push(a + b);
            case DIVISION -> {
                if (b == 0) {
                    throw new RuntimeException("division by zero");
                }
                stack.push(a / b);
            }
            case MULTIPLY -> stack.push(a * b);
            case MINUS -> stack.push(a - b);
            default -> throw new IllegalArgumentException("Unknown operation type " + operation.getType());
        }
    }

    @Override
    public void visit(NumberToken numberToken) throws IOException {
        stack.push(numberToken.getValue());
    }

    public Long end() {
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Unexpected end");
        } else {
            return stack.pop();
        }
    }
}
