package syntactic_analysis;

import grammars.Production;
import grammars.Symbol;
import ll_one_parser.LLOneAnalysisTable;
import tokens.Token;
import tokens.TokenType;

import java.util.Stack;

public class LLOneParser {
    private final LLOneAnalysisTable analysisTable;
    private final Symbol startSymbol;

    public LLOneParser(LLOneAnalysisTable analysisTable, Symbol startSymbol) {
        this.analysisTable = analysisTable;
        this.startSymbol = startSymbol;
    }

    public ASTNode parse(Stack<Token> inputStack) {
        Stack<ASTNode> nodeWorkingStack = new Stack<>();
        nodeWorkingStack.add(new ASTNode(Symbol.END_MARKER));
        ASTNode root = new ASTNode(startSymbol);
        nodeWorkingStack.add(root);

        while (!inputStack.isEmpty()) {
            ASTNode nextNode = nodeWorkingStack.pop();
            Token nextToken = inputStack.peek();

            // accept
            if (nextNode.getSymbol().equals(Symbol.END_MARKER) && nextToken.getValue().equals(Symbol.END_MARKER.name())) {
                break;
            }

            if (nextNode.getSymbol().equals(Symbol.EPSILON)) {
                continue;
            }

            boolean pop = nextNode.getSymbol().isTerminal() && nextNode.getSymbol().name().equals(nextToken.getValue());
            pop = pop || nextToken.getType() == TokenType.IDENTIFIER && nextNode.getSymbol().name().equals("ID");
            pop = pop || nextToken.getType() == TokenType.INTEGER_CONSTANT && nextNode.getSymbol().name().equals("CONST");
            pop = pop || nextToken.getType() == TokenType.FLOAT_CONSTANT && nextNode.getSymbol().name().equals("CONST");
            pop = pop || nextToken.getType() == TokenType.STRING_CONSTANT && nextNode.getSymbol().name().equals("STRING");

            // pop
            if (pop) {
                nextNode.setSymbol(new Symbol(nextToken.getValue(), true));
                inputStack.pop();
                continue;
            }

            Production nextProduction;

            if (nextToken.getType() == TokenType.IDENTIFIER) {
                nextProduction = analysisTable.getTableCell(nextNode.getSymbol(), new Symbol("ID", true));
            } else if (nextToken.getType() == TokenType.INTEGER_CONSTANT || nextToken.getType() == TokenType.FLOAT_CONSTANT) {
                nextProduction = analysisTable.getTableCell(nextNode.getSymbol(), new Symbol("CONST", true));
            } else if (nextToken.getType() == TokenType.STRING_CONSTANT) {
                nextProduction = analysisTable.getTableCell(nextNode.getSymbol(), new Symbol("STRING", true));
            } else {
                nextProduction = analysisTable.getTableCell(nextNode.getSymbol(), new Symbol(nextToken.getValue(), true));
            }

            // error
            if (nextProduction == null) {
                throw new RuntimeException("Parsing error"); // Hopefully more detailed error report later
            }

            for (int i = nextProduction.rightSide().size() - 1; i >= 0; i--) {
                Symbol symbol = nextProduction.rightSide().get(i);
                ASTNode child = new ASTNode(symbol);
                nextNode.addChild(child);
                nodeWorkingStack.push(child);
            }
        }

        return root;
    }
}
