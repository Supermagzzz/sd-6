package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class ParseVisitor implements TokenVisitor {
    private final List<Token> out = new ArrayList<>();
    private final Stack<Token> stack = new Stack<>();
    private int cursor = 0;
    private Token lastToken;

    @Override
    public void visit(Brace brace) throws ParseException {
        if (brace.getType() == Brace.Type.LEFT) {
            if (!stack.empty() && stack.peek().getClass() == Brace.class && ((Brace)stack.peek()).getType() == Brace.Type.RIGHT) {
                throw new ParseException("Unexpected open brace after closed brace", cursor);
            } else {
                stack.add(brace);
            }
        } else if (brace.getType() == Brace.Type.RIGHT) {
            while (!stack.empty() && !(stack.peek().getClass() == Brace.class && ((Brace)stack.peek()).getType() == Brace.Type.LEFT)) {
                out.add(stack.pop());
            }
            if (stack.empty() && !(stack.peek().getClass() == Brace.class && ((Brace)stack.peek()).getType() == Brace.Type.LEFT)) {
                throw new ParseException("Unexpected closed brace", cursor);
            }
            stack.pop();
        } else {
            throw new IllegalArgumentException("Unknown brace " + brace.getType());
        }
        lastToken = brace;
        cursor++;
    }

    @Override
    public void visit(Operation operation) throws ParseException {
        if (lastToken == null || (lastToken.getClass() != NumberToken.class &&
                !(lastToken.getClass() == Brace.class && ((Brace)lastToken).getType() == Brace.Type.RIGHT))) {
            throw new ParseException("Unexpected operation", cursor);
        }
        while (!stack.empty() && stack.peek().getClass() == Operation.class) {
            if (((Operation)stack.peek()).getPriority() < operation.getPriority()) {
                break;
            }
            out.add(stack.pop());
        }
        stack.add(operation);
        lastToken = operation;
        cursor++;
    }

    @Override
    public void visit(NumberToken numberToken) throws ParseException {
        if (lastToken != null && lastToken.getClass() != Operation.class &&
                !(lastToken.getClass() == Brace.class && ((Brace)lastToken).getType() == Brace.Type.LEFT)) {
            throw new ParseException("Unexpected number", cursor);
        }
        out.add(numberToken);
        lastToken = numberToken;
        cursor++;
    }

    public List<Token> end() throws ParseException {
        while (!stack.empty()) {
            if (stack.peek().getClass() != Operation.class) {
                throw new ParseException("Unexpected end", cursor);
            }
            out.add(stack.pop());
        }
        return out;
    }
}
