package org.example;

import java.util.List;
import java.util.Map;

public class Data implements IData {
    private final List<Character> axis = List.of('S', 'B', 'G', 'b', '(', 'a', ')');

    private final List<List<Rel>> relation = List.of(
        List.of(Rel.None,Rel.None,Rel.None,Rel.None,Rel.None,Rel.None,Rel.None),
        List.of(Rel.None,Rel.None,Rel.None,Rel.Eq,Rel.None,Rel.Eq,Rel.None),
        List.of(Rel.None,Rel.None,Rel.None,Rel.Gt,Rel.None,Rel.Gt,Rel.None),
        List.of(Rel.None,Rel.Eq,Rel.None,Rel.None,Rel.Ls,Rel.Ls,Rel.None),
        List.of(Rel.None,Rel.Ls,Rel.Eq,Rel.None,Rel.Ls,Rel.Ls,Rel.None),
        List.of(Rel.None,Rel.None,Rel.None,Rel.Gt,Rel.None,Rel.Gt,Rel.Eq),
        List.of(Rel.None,Rel.None,Rel.None,Rel.Gt,Rel.None,Rel.Gt,Rel.None)
    );

    private final Map<String, Character> grammar = Map.of(
        "bBb", 'S',
        "(G", 'B',
        "a", 'B',
        "Ba)", 'G'
    );

    private int getIndex(Character symbol) throws WrongGrammarException {
        for (int i = 0; i < axis.size(); i++) {
            if (axis.get(i) == symbol) return i;
        }

        throw new WrongGrammarException();
    }

    @Override
    public Rel compare(Character s1, Character s2) throws WrongGrammarException {
        return relation.get(getIndex(s1)).get(getIndex(s2));
    }

    @Override
    public Character getSymbolByString(String str) {
        return grammar.get(str);
    }
}
