package grammars;

public record Symbol(String name, boolean isTerminal) {
    public static Symbol EPSILON = new Symbol("eps", true);
    public static Symbol END_MARKER = new Symbol("$", true);

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Symbol symbol)) {
            return false;
        }

        return name.equals(symbol.name) && isTerminal == symbol.isTerminal;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
