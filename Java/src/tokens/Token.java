package tokens;

public class Token {
    private final TokenCategory category;
    private final TokenType type;
    private final String value;
    private final int code;

    public Token(TokenCategory category, TokenType type, String value, int code) {
        this.category = category;
        this.type = type;
        this.value = value;
        this.code = code;
    }

    public TokenCategory getCategory() {
        return category;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Token{" +
                "category=" + category +
                ", type=" + type +
                ", value='" + value + '\'' +
                ", code=" + code +
                '}';
    }
}
