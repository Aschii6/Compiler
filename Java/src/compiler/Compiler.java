package compiler;

import grammars.ContextFreeGrammar;
import grammars.GrammarCreator;
import lexical_analysis.Lexer;
import syntactic_analysis.ASTNode;
import syntactic_analysis.SyntaxAnalyzer;
import tokens.Token;
import utils.ASTFileWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Compiler {
    Lexer lexer = new Lexer();

    SyntaxAnalyzer syntaxAnalyzer;

    ContextFreeGrammar cfg;

    public Compiler() {
        cfg = GrammarCreator.createCFG("src/inputs/ll1grammar.txt");
        syntaxAnalyzer = new SyntaxAnalyzer(cfg);
    }

    public void compile(String programFile) {
        String code;
        try {
            code = Files.readString(Path.of(programFile), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }

        List<Token> tokens = lexer.tokenize(code);

        String outputFilePath = "src/outputs/tokens.txt";
        StringBuilder tokensContent = new StringBuilder();
        for (Token token : tokens) {
            tokensContent.append(token).append("\n");
        }

        try {
            Files.writeString(Path.of(outputFilePath), tokensContent.toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }

        ASTNode ast = syntaxAnalyzer.analyze(tokens);

        ASTFileWriter.writeToFile(ast, "src/outputs/ast.txt");
    }
}
