package org.example;

import java.util.ArrayDeque;
import java.util.Deque;

class WrongGrammarException extends Exception {}

public class Analyzer {
    private final IData data;
    public Deque<Character> stack = new ArrayDeque<>();

    public Analyzer(IData data) {
        this.data = data;
    }

    public boolean analyze(String str) {
        for (Character symbol : str.toCharArray()) {
            if (stack.isEmpty()) {
                stack.push(symbol);
                continue;
            }

            try {
                while (data.compare(stack.peek(), symbol) == Rel.Gt) {
                    fold();
                }
            } catch (WrongGrammarException e) {
                return false;
            }

            stack.push(symbol);
        }
        try {
            while (stack.isEmpty() || stack.peek() != 'S') {
                fold();
            }
        } catch (WrongGrammarException e) { return false; }

        return stack.peek() == 'S';
    }

    public void fold() throws WrongGrammarException {
        StringBuilder str = new StringBuilder();
        Character c;
        do {
            c = stack.pop();
            str.insert(0, c);
        } while (!stack.isEmpty() && data.compare(stack.peek(), c) != Rel.Ls);

        Character notTerminal = data.getSymbolByString(str.toString());
        if (notTerminal == null) throw new WrongGrammarException();

        stack.push(notTerminal);
    }
}
