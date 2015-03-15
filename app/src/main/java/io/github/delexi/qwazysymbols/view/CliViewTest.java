package io.github.delexi.qwazysymbols.view;

import io.github.delexi.qwazysymbols.core.Symbol;

public class CliViewTest {
    public static void main(String[] args) {
        View view = new CliView(24, 24);
        for (Symbol symbol : Symbol.values()) {
            System.out.print("X");
            view.updateDisplay(symbol);
            System.out.print("X");
        }
    }
}
