package syntactic_analysis;

import grammars.ContextFreeGrammar;
import ll_one_parser.AnalysisTableCreator;
import ll_one_parser.LLOneAnalysisTable;
import tokens.Token;

import java.util.List;
import java.util.Stack;

public class SyntaxAnalyzer {
    LLOneParser parser;

    public SyntaxAnalyzer(ContextFreeGrammar cfg) {
        LLOneAnalysisTable analysisTable = AnalysisTableCreator.createLLOneAnalysisTable(cfg);

        parser = new LLOneParser(analysisTable, cfg.getStartSymbol());
    }

    public ASTNode analyze(List<Token> tokens) {
        Stack<Token> inputStack = new Stack<>();

        for (int i = tokens.size() - 1; i >= 0; i--) {
            inputStack.push(tokens.get(i));
        }

        try {
            return parser.parse(inputStack);
        } catch (Exception e) {
            System.out.println("Rejected");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
