package tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenUtils {
    private static final Map<String, Token> valueToTokenMap = new HashMap<>(){{
        put("main", new Token(TokenCategory.KEYWORD, TokenType.MAIN, "main", 10));
        put("int", new Token(TokenCategory.KEYWORD, TokenType.INT, "int", 11));
        put("float", new Token(TokenCategory.KEYWORD, TokenType.FLOAT, "float", 12));
        put("cin", new Token(TokenCategory.KEYWORD, TokenType.CIN, "cin", 13));
        put("cout", new Token(TokenCategory.KEYWORD, TokenType.COUT, "cout", 14));
        put("if", new Token(TokenCategory.KEYWORD, TokenType.IF, "if", 15));
        put("else", new Token(TokenCategory.KEYWORD, TokenType.ELSE, "else", 16));
        put("while", new Token(TokenCategory.KEYWORD, TokenType.WHILE, "while", 17));
        put("+", new Token(TokenCategory.ARITHMETIC_OPERATOR, TokenType.PLUS, "+", 40));
        put("-", new Token(TokenCategory.ARITHMETIC_OPERATOR, TokenType.MINUS, "-", 41));
        put("*", new Token(TokenCategory.ARITHMETIC_OPERATOR, TokenType.MULTIPLY, "*", 42));
        put("/", new Token(TokenCategory.ARITHMETIC_OPERATOR, TokenType.DIVIDE, "/", 43));
        put("%", new Token(TokenCategory.ARITHMETIC_OPERATOR, TokenType.MODULO, "%", 44));
        put("==", new Token(TokenCategory.COMPARISON_OPERATOR, TokenType.EQUAL, "==", 50));
        put("!=", new Token(TokenCategory.COMPARISON_OPERATOR, TokenType.NOT_EQUAL, "!=", 51));
        put("<=", new Token(TokenCategory.COMPARISON_OPERATOR, TokenType.LESS_THAN_OR_EQUAL, "<=", 52));
        put("<", new Token(TokenCategory.COMPARISON_OPERATOR, TokenType.LESS_THAN, "<", 53));
        put(">=", new Token(TokenCategory.COMPARISON_OPERATOR, TokenType.GREATER_THAN_OR_EQUAL, ">=", 54));
        put(">", new Token(TokenCategory.COMPARISON_OPERATOR, TokenType.GREATER_THAN, ">", 55));
        put("&&", new Token(TokenCategory.LOGICAL_OPERATOR, TokenType.AND, "&&", 60));
        put("||", new Token(TokenCategory.LOGICAL_OPERATOR, TokenType.OR, "||", 61));
        put("=", new Token(TokenCategory.ASSIGNMENT_OPERATOR, TokenType.ASSIGNMENT, "=", 70));
        put("<<", new Token(TokenCategory.IO_OPERATOR, TokenType.INPUT_OPERATOR, "<<", 81));
        put(">>", new Token(TokenCategory.IO_OPERATOR, TokenType.OUTPUT_OPERATOR, ">>", 82));
        put("{", new Token(TokenCategory.SEPARATOR, TokenType.OPEN_BRACE, "{", 90));
        put("}", new Token(TokenCategory.SEPARATOR, TokenType.CLOSE_BRACE, "}", 91));
        put("(", new Token(TokenCategory.SEPARATOR, TokenType.OPEN_PARENTHESIS, "(", 92));
        put(")", new Token(TokenCategory.SEPARATOR, TokenType.CLOSE_PARENTHESIS, ")", 93));
        put(";", new Token(TokenCategory.SEPARATOR, TokenType.SEMICOLON, ";", 94));
    }};

    public static Token getToken(String value) throws Exception {
        if (!valueToTokenMap.containsKey(value)) {
            throw new Exception("Value not in existing tokens.");
        }

        return valueToTokenMap.get(value);
    }

    public static Token getToken(int code) throws Exception {
        for (Token token : valueToTokenMap.values()) {
            if (token.getCode() == code) {
                return token;
            }
        }

        throw new Exception("Code not in existing tokens.");
    }

    public static Token getIdentifierToken(String value) {
        return new Token(TokenCategory.IDENTIFIER, TokenType.IDENTIFIER, value, 0);
    }

    public static Token getIntegerConstantToken(String value) {
        return new Token(TokenCategory.CONSTANT, TokenType.INTEGER_CONSTANT, value, 1);
    }

    public static Token getFloatConstantToken(String value) {
        return new Token(TokenCategory.CONSTANT, TokenType.FLOAT_CONSTANT, value, 1);
    }

    public static Token getStringConstantToken(String value) {
        return new Token(TokenCategory.CONSTANT, TokenType.STRING_CONSTANT, value, 1);
    }

    public static Token getEndMarkerToken() {
        return new Token(TokenCategory.END_MARKER, TokenType.END_MARKER, "$", -1);
    }

    public static Map<TokenCategory, List<String>> getValuesByCategory() {
        Map<TokenCategory, List<String>> valuesByCategory = new HashMap<>();
        for (TokenCategory category : TokenCategory.values()) {
            if (category == TokenCategory.IDENTIFIER || category == TokenCategory.CONSTANT) {
                continue;
            }

            valuesByCategory.put(category, getValues(category));
        }
        return valuesByCategory;
    }

    private static List<String> getValues(TokenCategory category) {
        List<String> values = new ArrayList<>();
        for (Token token : valueToTokenMap.values()) {
            if (token.getCategory() == category) {
                values.add(token.getValue());
            }
        }

        values.sort((v1, v2) -> {
            try {
                return getToken(v1).getCode() - getToken(v2).getCode();
            } catch (Exception e) {
                return 0;
            }
        });

        return values;
    }
}
