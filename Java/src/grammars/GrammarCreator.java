package grammars;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GrammarCreator {
    public static ContextFreeGrammar createCFG(String inputFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            String startSymbolValue = lines.get(0);
            if (!startSymbolValue.startsWith("_")) {
                throw new IllegalArgumentException("Start symbol must start with _");
            }

            Symbol startSymbol = new Symbol(startSymbolValue.substring(1), false);
            ContextFreeGrammar cfGrammar = new ContextFreeGrammar(startSymbol);

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("->");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid production: " + line);
                }

                String leftSide = parts[0].trim();
                if (!leftSide.startsWith("_")) {
                    throw new IllegalArgumentException("Non-terminal symbol must start with _");
                }
                leftSide = leftSide.substring(1);

                String[] rightSide = parts[1].trim().split(" ");
                if (rightSide.length < 1) {
                    throw new IllegalArgumentException("Invalid production: " + line);
                }

                List<Symbol> rightSideList = new ArrayList<>();
                for (String symbol : rightSide) {
                    if (symbol.startsWith("_")) {
                        rightSideList.add(new Symbol(symbol.substring(1), false));
                    } else {
                        rightSideList.add(new Symbol(symbol, true));
                    }
                }

                cfGrammar.addProduction(new Production(new Symbol(leftSide, false), rightSideList));
            }

            return cfGrammar;
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }
}
