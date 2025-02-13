package lexical_analysis;

import FA.DeterministicFiniteAutomaton;
import FA.FiniteAutomatonCreator;
import tokens.Token;
import tokens.TokenCategory;
import tokens.TokenUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lexer {
    private String input;
    private int index;
    private int line;

    private List<Token> tokens;

    Map<TokenCategory, List<String>> valuesByCategory = TokenUtils.getValuesByCategory();

    DeterministicFiniteAutomaton idFA;
    DeterministicFiniteAutomaton intFA;
    DeterministicFiniteAutomaton floatFA;

    public Lexer() {
        createFiniteAutomata();
    }

    public List<Token> tokenize(String input) {
        this.input = input;
        this.index = 0;
        this.line = 1;

        tokens = new ArrayList<>();

        while (index < input.length()) {
            char currentChar = input.charAt(index);

            if (Character.isWhitespace(currentChar)) {
                if (currentChar == '\n') {
                    line++;
                }
                index++;
                continue;
            }

            Token token;
            try {
                token = nextToken();
            } catch (Exception e) {
                throw new RuntimeException("Unexpected error while extracting token: " + e.getMessage());
            }

            if (token != null) {
                tokens.add(token);
            } else {
                throw new RuntimeException("Unknown character: " + currentChar + " on line " + line);
            }
        }

        return tokens;
    }

    private Token nextToken() throws Exception {
        String value = tryExtractValue(TokenCategory.KEYWORD);
        if (value != null) {
            return TokenUtils.getToken(value);
        }

        value = tryExtractIdentifier();
        if (value != null) {
            return TokenUtils.getIdentifierToken(value);
        }

        value = tryExtractFloat();
        if (value != null) {
            return TokenUtils.getFloatConstantToken(value);
        }

        value = tryExtractInt();
        if (value != null) {
            return TokenUtils.getIntegerConstantToken(value);
        }

        value = tryExtractString();
        if (value != null) {
            return TokenUtils.getStringConstantToken(value);
        }

        value = tryExtractValue(TokenCategory.ARITHMETIC_OPERATOR);
        if (value != null) {
            return TokenUtils.getToken(value);
        }

        value = tryExtractValue(TokenCategory.IO_OPERATOR);
        if (value != null) {
            return TokenUtils.getToken(value);
        }

        value = tryExtractValue(TokenCategory.COMPARISON_OPERATOR);
        if (value != null) {
            return TokenUtils.getToken(value);
        }

        value = tryExtractValue(TokenCategory.LOGICAL_OPERATOR);
        if (value != null) {
            return TokenUtils.getToken(value);
        }

        value = tryExtractValue(TokenCategory.ASSIGNMENT_OPERATOR);
        if (value != null) {
            return TokenUtils.getToken(value);
        }

        value = tryExtractValue(TokenCategory.SEPARATOR);
        if (value != null) {
            return TokenUtils.getToken(value);
        }

        return null;
    }

    private String tryExtractValue(TokenCategory category) {
        for (String value : valuesByCategory.get(category)) {
            if (input.startsWith(value, index)) {
                index += value.length();
                return value;
            }
        }
        
        return null;
    }

    private String tryExtractIdentifier() {
        return tryExtractFromDFA(idFA);
    }

    private String tryExtractFloat() {
        if (!tokens.isEmpty() && (tokens.get(tokens.size() - 1).getCategory() == TokenCategory.IDENTIFIER ||
                tokens.get(tokens.size() - 1).getCategory() == TokenCategory.CONSTANT)) {
            return null;
        }

        return tryExtractFromDFA(floatFA);
    }

    private String tryExtractInt() {
        if (!tokens.isEmpty() && (tokens.get(tokens.size() - 1).getCategory() == TokenCategory.IDENTIFIER ||
                tokens.get(tokens.size() - 1).getCategory() == TokenCategory.CONSTANT)) {
            return null;
        }

        return tryExtractFromDFA(intFA);
    }

    private String tryExtractFromDFA(DeterministicFiniteAutomaton DFA) {
        int firstWhiteSpace = input.indexOf(' ', index);
        firstWhiteSpace = firstWhiteSpace == -1 ? input.indexOf('\n', index) : firstWhiteSpace;
        firstWhiteSpace = firstWhiteSpace == -1 ? input.indexOf('\t', index) : firstWhiteSpace;
        firstWhiteSpace = firstWhiteSpace == -1 ? input.length() : firstWhiteSpace;

        String longestPrefix = DFA.getLongestPrefix(input.substring(index, firstWhiteSpace));

        if (!longestPrefix.isEmpty()) {
            index += longestPrefix.length();
            return longestPrefix;
        }

        return null;
    }

    private String tryExtractString() {
        if (input.charAt(index) != '"') {
            return null;
        }

        int end = input.indexOf('"', index + 1);
        if (end == -1) {
            throw new RuntimeException("String not closed on line " + line);
        }

        String string = input.substring(index + 1, end);
        index = end + 1;
        return string;
    }

    private void createFiniteAutomata() {
        try {
            String input = Files.readString(Path.of("src/inputs/FAs/FA_ID.txt"),
                    StandardCharsets.UTF_8);
            idFA = FiniteAutomatonCreator.createDFA(input);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }

        try {
            String input = Files.readString(Path.of("src/inputs/FAs/FA_INT.txt"),
                    StandardCharsets.UTF_8);
            intFA = FiniteAutomatonCreator.createDFA(input);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file");
        }

        try {
            String input = Files.readString(Path.of("src/inputs/FAs/FA_FLOAT.txt"),
                    StandardCharsets.UTF_8);
            floatFA = FiniteAutomatonCreator.createDFA(input);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file");
        }
    }
}
