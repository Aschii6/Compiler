package ll_one_parser;

import grammars.ContextFreeGrammar;
import grammars.Production;
import grammars.Symbol;

import java.util.*;

public class AnalysisTableCreator {
    public static LLOneAnalysisTable createLLOneAnalysisTable(ContextFreeGrammar cfg) {
        Map<Symbol, Set<Symbol>> first1 = getFirst1(cfg);

        Map<Symbol, Set<Symbol>> follow1 = getFollow1(cfg, first1);

        LLOneAnalysisTable analysisTable = new LLOneAnalysisTable();

        Map<Symbol, List<Production>> productions = cfg.getProductions();
        Set<Symbol> nonTerminalSymbols = cfg.getNonTerminalSymbols();

        for (Symbol nt : nonTerminalSymbols) {
            List<Production> productionsForNT = productions.get(nt);

            for (Production production : productionsForNT) {
                Set<Symbol> first = getFirst1OfProduction(production, first1);

                for (Symbol symbol : first) {
                    if (symbol.equals(Symbol.EPSILON)) {
                        for (Symbol followSymbol : follow1.get(nt)) {
                            analysisTable.addEntry(nt, followSymbol, production);
                        }
                    } else {
                        analysisTable.addEntry(nt, symbol, production);
                    }
                }
            }
        }

        return analysisTable;
    }

    private static Map<Symbol, Set<Symbol>> getFirst1(ContextFreeGrammar cfg) {
        Map<Symbol, Set<Symbol>> F = new HashMap<>();
        Map<Symbol, List<Production>> productions = cfg.getProductions();
        Set<Symbol> nonTerminalSymbols = cfg.getNonTerminalSymbols();
        Set<Symbol> terminalSymbols = cfg.getTerminalSymbols();

        for (Symbol nt : nonTerminalSymbols) {
            F.put(nt, new HashSet<>());
        }
        for (Symbol t : terminalSymbols) {
            F.put(t, new HashSet<>());
            F.get(t).add(t);
        }

        boolean changed = true;
        while (changed) {
            changed = false;

            for (Symbol nt : nonTerminalSymbols) {
                for (Production production : productions.get(nt)) {
                    List<Symbol> right = production.rightSide();
                    boolean allHaveEps = true;

                    for (Symbol symbol : right) {
                        if (F.get(symbol).contains(Symbol.EPSILON)) {
                            Set<Symbol> temp = new HashSet<>(F.get(symbol));
                            temp.remove(Symbol.EPSILON);

                            if (F.get(nt).addAll(temp)) {
                                changed = true;
                            }
                        } else {
                            if (F.get(nt).addAll(F.get(symbol))) {
                                changed = true;
                            }

                            allHaveEps = false;
                            break;
                        }
                    }

                    if (allHaveEps) {
                        if (F.get(nt).add(Symbol.EPSILON)) {
                            changed = true;
                        }
                    }
                }
            }
        }

        return F;
    }

    private static Map<Symbol, Set<Symbol>> getFollow1(ContextFreeGrammar cfg, Map<Symbol, Set<Symbol>> first1) {
        Map<Symbol, Set<Symbol>> F = new HashMap<>();
        Map<Symbol, List<Production>> productions = cfg.getProductions();
        Set<Symbol> nonTerminalSymbols = cfg.getNonTerminalSymbols();
        Symbol startSymbol = cfg.getStartSymbol();

        for (Symbol nt : nonTerminalSymbols) {
            F.put(nt, new HashSet<>());
        }
        F.get(startSymbol).add(Symbol.END_MARKER);

        boolean changed = true;

        while (changed) {
            changed = false;

            for (List<Production> prods : productions.values()) {
                for (Production production : prods) {
                    List<Symbol> right = production.rightSide();

                    for (int i = 0; i < right.size(); i++) {
                        Symbol symbol = right.get(i);

                        if (symbol.isTerminal()) {
                            continue;
                        }

                        Set<Symbol> temp = new HashSet<>();
                        boolean allHaveEps = true;

                        for (int j = i + 1; j < right.size(); j++) {
                            Symbol nextSymbol = right.get(j);

                            if (first1.get(nextSymbol).contains(Symbol.EPSILON)) {
                                temp.addAll(first1.get(nextSymbol));
                                temp.remove(Symbol.EPSILON);
                            } else {
                                temp.addAll(first1.get(nextSymbol));
                                allHaveEps = false;
                                break;
                            }
                        }

                        if (allHaveEps) { // Also true when nt is the last symbol in the production, which is good
                            temp.addAll(F.get(production.leftSide()));
                        }

                        if (F.get(symbol).addAll(temp)) {
                            changed = true;
                        }
                    }
                }
            }
        }

        return F;
    }

    private static Set<Symbol> getFirst1OfProduction(Production production, Map<Symbol, Set<Symbol>> first1) {
        Set<Symbol> result = new HashSet<>();
        List<Symbol> right = production.rightSide();

        boolean allHaveEps = true;

        for (Symbol symbol : right) {
            if (first1.get(symbol).contains(Symbol.EPSILON)) {
                Set<Symbol> temp = new HashSet<>(first1.get(symbol));
                temp.remove(Symbol.EPSILON);

                result.addAll(temp);
            } else {
                result.addAll(first1.get(symbol));
                allHaveEps = false;
                break;
            }
        }

        if (allHaveEps) {
            result.add(Symbol.EPSILON);
        }

        return result;
    }
}
