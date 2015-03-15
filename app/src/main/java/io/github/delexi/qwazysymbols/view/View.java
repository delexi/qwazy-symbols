package io.github.delexi.qwazysymbols.view;

import io.github.delexi.qwazysymbols.core.Symbol;

public interface View {
    void updateDisplay(Symbol currentSymbol);

    /**
     * Return maximum number of symbols shown per second.
     * @return maximum number of symbols shown per second.
     */
    int maximumSymbolsPerSecond();
}
