package io.github.delexi.qwazysymbols.view;

import io.github.delexi.qwazysymbols.core.Symbol;

public class CliViewTest {
    public static void main(String[] args) {
        View view = new CliView( 100, 50);
        for (Symbol symbol : Symbol.values()) {
            view.updateDisplay(symbol);
        }
    }
}
