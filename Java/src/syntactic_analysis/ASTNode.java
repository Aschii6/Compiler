package syntactic_analysis;

import grammars.Symbol;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    private Symbol symbol;
    private final List<ASTNode> children = new ArrayList<>();

    public ASTNode(Symbol symbol) {
        this.symbol = symbol;
    }

    public void addChild(ASTNode child) {
        children.add(child);
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
