package org.example;

public interface IData {
    Rel compare(Character s1, Character s2) throws WrongGrammarException;
    Character getSymbolByString(String str);
}
