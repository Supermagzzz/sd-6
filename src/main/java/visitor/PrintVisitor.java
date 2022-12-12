package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;

import java.io.IOException;
import java.io.OutputStream;

public class PrintVisitor implements TokenVisitor {
    private OutputStream out;
    private boolean firstToken;

    public PrintVisitor(OutputStream stream) {
        this.out = stream;
        this.firstToken = true;
    }

    @Override
    public void visit(Brace brace) throws IOException {
        checkFirst();
        switch (brace.getType()) {
            case LEFT -> write("LEFT");
            case RIGHT -> write("RIGHT");
            default -> throw new IllegalArgumentException("Unknown brace type in PrintVisitor " + brace.getType());
        }
    }

    @Override
    public void visit(Operation operation) throws IOException {
        checkFirst();
        switch (operation.getType()) {
            case PLUS -> write("PLUS");
            case DIVISION -> write("DIV");
            case MULTIPLY -> write("MUL");
            case MINUS -> write("MINUS");
            default -> throw new IllegalArgumentException("Unknown operation type in PrintVisitor " + operation.getType());
        }
    }

    @Override
    public void visit(NumberToken numberToken) throws IOException {
        checkFirst();
        write("NUMBER(" + numberToken.getValue() + ")");
    }

    private void write(String s) throws IOException {
        out.write(s.getBytes());
    }

    private void checkFirst() throws IOException {
        if (firstToken) {
            firstToken = false;
        } else {
            out.write(" ".getBytes());
        }
    }
}
