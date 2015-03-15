package io.github.delexi.qwazysymbols.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SymbolLogicImpl implements SymbolLogic {

    private Random randGen;
    private long seed;

    public SymbolLogicImpl(long seed) {
        randGen = new Random(this.seed = seed);
    }

    public SymbolLogicImpl() {
        startNewGame();
        randGen = new Random(seed);
    }

    @Override
    public Iterable<Symbol> generateSymbolSequence(int length) {
        randGen.setSeed(this.seed);
        List<Symbol> symbols = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            symbols.add(Symbol.values()[randGen.nextInt(Symbol.values().length - 1)]);
        }
        return symbols;
    }

    @Override
    public boolean checkHead(Iterator<Symbol> sequence, Symbol toCheck) {
        return sequence.next().equals(toCheck);
    }

    @Override
    public void startNewGame() {
        this.seed = new Random().nextLong();
    }
}
