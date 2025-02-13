package FA;

public class FiniteAutomatonCreator {
    public static DeterministicFiniteAutomaton createDFA(String input) {
        String[] parts = input.split(";");

        String initialState = parts[0].trim();
        DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton(initialState);

        String[] transitionsLines = parts[1].split("\n");
        for (String line : transitionsLines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] transitionParts = line.split(", ");
            String stateFrom = transitionParts[0];
            String stateTo = transitionParts[transitionParts.length - 1];

            for (int i = 1; i < transitionParts.length - 1; i++) {
                dfa.addTransition(stateFrom, transitionParts[i], stateTo);
            }
        }

        String[] finalStatesParts = parts[2].split("\n");
        for (String finalState : finalStatesParts) {
            dfa.addFinalState(finalState.trim());
        }

        return dfa;
    }
}
