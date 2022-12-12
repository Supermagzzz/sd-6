package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;

import java.io.IOException;
import java.text.ParseException;

public interface TokenVisitor {
    void visit(NumberToken numberToken) throws IOException, ParseException;
    void visit(Brace brace) throws IOException, ParseException;
    void visit(Operation operation) throws IOException, ParseException;
}
