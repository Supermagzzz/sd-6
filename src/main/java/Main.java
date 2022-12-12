import token.Token;
import tokenizer.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParseVisitor;
import visitor.PrintVisitor;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String example = scanner.nextLine();

            Tokenizer tokenizer = new Tokenizer(example);
            List<Token> tokens = tokenizer.parse();

            ParseVisitor parseVisitor = new ParseVisitor();
            for (Token token : tokens) {
                token.accept(parseVisitor);
            }
            List<Token> polish = parseVisitor.end();

            PrintVisitor printVisitor = new PrintVisitor(System.out);
            CalcVisitor calcVisitor = new CalcVisitor();

            for (Token token : polish) {
                token.accept(printVisitor);
            }
            System.out.println();

            for (Token token : polish) {
                token.accept(calcVisitor);
            }
            System.out.println(calcVisitor.end());
        } catch (ParseException | IllegalArgumentException e) {
            System.out.println("invalid expression");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
