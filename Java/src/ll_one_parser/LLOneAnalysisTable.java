package ll_one_parser;

import grammars.Production;
import grammars.Symbol;

import java.util.HashMap;
import java.util.Map;

public class LLOneAnalysisTable {
    private final Map<Symbol, Map<Symbol, Production>> table = new HashMap<>();

    public void addEntry(Symbol nt, Symbol t, Production production) {
        if (!nt.equals(production.leftSide())) {
            throw new IllegalArgumentException("Non-terminal symbol and left side of the production do not match");
        }

        if (!table.containsKey(nt)) {
            table.put(nt, new HashMap<>());
        } else if (table.get(nt).containsKey(t)) {
            if (!table.get(nt).get(t).equals(production)) {
                throw new RuntimeException("Conflict in the table: " + nt + ", " + t + ": " +
                        production + " and " + table.get(nt).get(t));
            }
        }

        table.get(nt).put(t, production);
    }

    public Production getTableCell(Symbol nt, Symbol t) {
        if (!table.containsKey(nt)) {
            return null;
        }

        return table.get(nt).get(t);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Symbol nt : table.keySet()) {
            sb.append(nt).append(" -> ");

            for (Symbol t : table.get(nt).keySet()) {
                sb.append(t).append(": ").append(table.get(nt).get(t)).append(", ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
