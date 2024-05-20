package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestData implements IData {
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

    public int getIndex(Character symbol) throws WrongGrammarException {
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

class AnalyzerTest {
    private final TestData data = new TestData();

    @Test
    void getIndex() {
        try {
            assertEquals(6, data.getIndex(')'));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void compare() {
        try {
            assertEquals(Rel.None, data.compare('S', 'a'));
            assertEquals(Rel.Eq, data.compare('b', 'B'));
            assertEquals(Rel.Ls, data.compare('(', '('));
            assertEquals(Rel.Gt, data.compare(')', 'a'));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void fold() {
        Analyzer analyzer = new Analyzer(data);
        analyzer.stack.push('b');
        analyzer.stack.push('(');
        analyzer.stack.push('B');
        analyzer.stack.push('a');
        analyzer.stack.push(')');
        try {
            analyzer.fold();
            assertEquals('G', analyzer.stack.pop());
            assertEquals('(', analyzer.stack.pop());
            assertEquals('b', analyzer.stack.pop());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void analyze() {
        Analyzer analyzer = new Analyzer(data);
        try {
            assertTrue(analyzer.analyze("b(aa)b"));
        } catch (Exception e) {
            fail();
        }
    }
}