package FA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeterministicFiniteAutomaton {
    private final List<String> states = new ArrayList<>();
    private final List<String> alphabet = new ArrayList<>();
    private final Map<String, Map<String, String>> transitions = new HashMap<>();
    private final String initialState;
    private final List<String> finalStates = new ArrayList<>();

    public DeterministicFiniteAutomaton(String initialState) {
        this.initialState = initialState;
    }

    public void addTransition(String stateFrom, String symbol, String stateTo) {
        if (!states.contains(stateFrom)) {
            states.add(stateFrom);
        }
        if (!states.contains(stateTo)) {
            states.add(stateTo);
        }
        if (!alphabet.contains(symbol)) {
            alphabet.add(symbol);
        }

        if (!transitions.containsKey(stateFrom)) {
            transitions.put(stateFrom, new HashMap<>());
            transitions.get(stateFrom).put(symbol, stateTo);
        } else {
            if (transitions.get(stateFrom).containsKey(symbol)) {
                throw new RuntimeException("Non-deterministic finite automaton");
            }

            transitions.get(stateFrom).put(symbol, stateTo);
        }
    }

    public void addFinalState(String finalState) {
        finalStates.add(finalState);
    }

    public boolean isSequenceAccepted(String sequence) {
        String currentState = initialState;

        for (int i = 0; i < sequence.length(); i++) {
            String symbol = String.valueOf(sequence.charAt(i)); // symbols max one character
            Map<String, String> symbolMap = transitions.get(currentState);
            if (symbolMap == null) {
                return false;
            }

            currentState = symbolMap.get(symbol);
            if (currentState == null) {
                return false;
            }
        }

        return finalStates.contains(currentState);
    }

    public String getLongestPrefix(String sequence) {
        for (int i = sequence.length(); i > 0; i--) {
            String subSequence = sequence.substring(0, i);
            if (isSequenceAccepted(subSequence)) {
                return subSequence;
            }
        }

        return "";
    }
}
