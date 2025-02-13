package grammars;

import java.util.*;

public class ContextFreeGrammar {
    private final Set<Symbol> nonTerminalSymbols = new HashSet<>();
    private final Set<Symbol> terminalSymbols = new HashSet<>();
    private final Map<Symbol, List<Production>> productions = new HashMap<>();
    private final Symbol startSymbol;

    public ContextFreeGrammar(Symbol startSymbol) {
        this.startSymbol = startSymbol;

        nonTerminalSymbols.add(startSymbol);
    }

    public void addProduction(Production production) {
        if (!productions.containsKey(production.leftSide())) {
            productions.put(production.leftSide(), new ArrayList<>());
        }

        productions.get(production.leftSide()).add(production);

        nonTerminalSymbols.add(production.leftSide());

        for (Symbol symbol : production.rightSide()) {
            if (symbol.isTerminal()) {
                terminalSymbols.add(symbol);
            } else {
                nonTerminalSymbols.add(symbol);
            }
        }

        if (!Collections.disjoint(nonTerminalSymbols, terminalSymbols)) {
            throw new RuntimeException("Non-terminal symbols and terminal symbols must be disjoint.");
        }
    }

    public Set<Symbol> getNonTerminalSymbols() {
        return nonTerminalSymbols;
    }

    public Set<Symbol> getTerminalSymbols() {
        return terminalSymbols;
    }

    public Map<Symbol, List<Production>> getProductions() {
        return productions;
    }

    public Symbol getStartSymbol() {
        return startSymbol;
    }

    @Override
    public String toString() {
        return "ContextFreeGrammar{" +
                "\nnonTerminalSymbols=" + nonTerminalSymbols +
                "\nterminalSymbols=" + terminalSymbols +
                "\nproductions=" + productions +
                "\nstartSymbol=" + startSymbol +
                '}';
    }
}
