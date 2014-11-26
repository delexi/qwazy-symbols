package io.github.delexi.qwazysymbols.core;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by delexi on 11/26/14.
 */
public interface Logic {
    /**
     * Returns a random sequence of {@code length} symbols.
     * @param length number of symbols to be generated
     * @return a sequence of random symbols
     */
    Iterable<Symbol> generateSymbolSequence(int length);

    /**
     * Check if {@code toCheck} matches the head of {@code sequence}.
     * This advances the given Iterator by one element.
     * @param sequence the sequence who's head to compare
     * @param toCheck the input compared to head
     * @return {@code true} if head and {@code toCheck} are equal, false otherwise
     */
    boolean checkHead(Iterator<Symbol> sequence, Symbol toCheck);
}
