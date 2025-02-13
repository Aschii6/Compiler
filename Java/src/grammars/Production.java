package grammars;

import java.util.List;

public record Production(Symbol leftSide, List<Symbol> rightSide) {
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(leftSide);
        builder.append(" -> ");

        for (int i = 0; i < rightSide.size(); i++) {
            builder.append(rightSide.get(i));

            if (i < rightSide.size() - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Production production)) {
            return false;
        }

        return leftSide.equals(production.leftSide) && rightSide.equals(production.rightSide);
    }
}
