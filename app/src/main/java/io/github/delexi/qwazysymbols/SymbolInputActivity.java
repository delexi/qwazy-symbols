package io.github.delexi.qwazysymbols;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.github.delexi.qwazysymbols.core.Symbol;
import io.github.delexi.qwazysymbols.core.SymbolLogic;
import io.github.delexi.qwazysymbols.core.SymbolLogicImpl;
import io.github.delexi.qwazysymbols.view.ConnectionMachineView;

import static android.graphics.Paint.Style.FILL;

public class SymbolInputActivity extends FragmentActivity
        implements View.OnClickListener {

    private static final String DEBUG_TAG = SymbolInputActivity.class.toString();

    private static abstract class DefaultDrawable extends Drawable {
        protected Paint paint;

        public DefaultDrawable(int color, Paint.Style style, int strokeWidth) {
            paint = new Paint();
            paint.setColor(color);
            paint.setStyle(style);
            paint.setStrokeWidth(strokeWidth);
        }

        @Override
        public void setAlpha(int i) {}

        @Override
        public void setColorFilter(ColorFilter colorFilter) {}

        @Override
        public int getOpacity() {
            return 0;
        }
    }

    private static class CircleDrawable extends DefaultDrawable {

        public CircleDrawable() {
            super(0xffffffaa, FILL, 2);
        }

        @Override
        public void draw(Canvas canvas) {
//            canvas.drawCircle(50, 50, 50, paint);
        }
    }

    private static class RectangleDrawable extends DefaultDrawable {

        public RectangleDrawable() {
            super(0xffffffaa, FILL, 2);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawRect(20, 20, 50, 50, paint);
        }

    }

    private static class TriangleDrawable extends DefaultDrawable {

        public TriangleDrawable() {
            super(0xffffffaa, FILL, 2);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawLines(new float [] {
                    55f, 20f, 70f, 70f,
                    70f, 70f, 20f, 70f,
                    20f, 70f, 55f, 20f,
            }, paint);
        }
    }

    private static class CrossDrawable extends DefaultDrawable {

        public CrossDrawable() {
            super(0xffffffaa, FILL, 2);
        }

        @Override
        public void draw(Canvas canvas) {
            // TODO
        }
    }

    private int level = 1;
    private Iterator<Symbol> currentSymbols;

    private io.github.delexi.qwazysymbols.view.View symbolsView;
    private SymbolLogic symbolLogic;

    private Button btnNext;
    private ImageButton btnTopRight;
    private ImageButton btnBottomLeft;
    private ImageButton btnBottomRight;
    private ImageButton btnTopLeft;
    private Map<ImageButton, Symbol> imageButtonSymbolMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_symbol_input);

        this.symbolLogic = MainActivity.getTheSymbolLogic();
        this.symbolsView = MainActivity.getTheSymbolsView();

        symbolsView.updateDisplay(Symbol.EMPTY);
        Log.d(DEBUG_TAG, "Set display to EMPTY");

        this.imageButtonSymbolMap = new HashMap<>();

        btnTopLeft = (ImageButton) findViewById(R.id.btnTopLeft);
        btnTopLeft.setImageResource(R.drawable.circle_green_200x200);
        btnTopLeft.invalidate();
        btnTopLeft.setOnClickListener(this);
        imageButtonSymbolMap.put(btnTopLeft, Symbol.CIRCLE);

        btnTopRight = (ImageButton) findViewById(R.id.btnTopRight);
        btnTopRight.setImageResource(R.drawable.rect_blue_200x200);
        btnTopRight.invalidate();
        btnTopRight.setOnClickListener(this);
        imageButtonSymbolMap.put(btnTopRight, Symbol.SQUARE);

        btnBottomLeft = (ImageButton) findViewById(R.id.btnBottomLeft);
        btnBottomLeft.setImageResource(R.drawable.cross_red_200x200);
        btnBottomLeft.invalidate();
        btnBottomLeft.setOnClickListener(this);
        imageButtonSymbolMap.put(btnBottomLeft, Symbol.CROSS);

        btnBottomRight = (ImageButton) findViewById(R.id.btnBottomRight);
        btnBottomRight.setImageResource(R.drawable.triangle_yellow_200x200);
        btnBottomRight.invalidate();
        btnBottomRight.setOnClickListener(this);
        imageButtonSymbolMap.put(btnBottomRight, Symbol.TRIANGLE);

        this.btnNext = (Button) findViewById(R.id.btnNext);
        this.btnNext.setOnClickListener(this);

        setEnabled(btnNext, true);
        setEnabledSymbolButtons(false);
    }

    @Override
    public void onClick(View view) {
        if (view == btnNext) {
            setEnabled(btnNext, false);
            setEnabledSymbolButtons(false);
            Iterable<Symbol> symbols = symbolLogic.generateSymbolSequence(level);
            this.currentSymbols = symbols.iterator();
            showSymbols(symbols);
        }
        // One of the four symbol image buttons
        else if (view instanceof ImageButton) {
            Symbol symbol = imageButtonSymbolMap.get(view);
            if (symbol != null) {
                Log.d(DEBUG_TAG, "Checking Symbol: " + symbol);
                if (!symbolLogic.checkHead(currentSymbols, symbol)) {
                    // Game is over!
                    Dialog endDialog = getGameEndDialog(level);
                    endDialog.show();
                    resetGame();
                } else if (!currentSymbols.hasNext()) {
                    // Completed the current level!
                    Dialog levelCompleteDialog = getLevelCompleteDialog(level);
                    levelCompleteDialog.show();
                    nextLevel();
                }
            }
        }
    }

    private void resetGame() {
        this.level = 1;
        this.currentSymbols = null;
        setEnabledSymbolButtons(false);
        setEnabled(btnNext, true);
    }

    private void nextLevel() {
        this.level++;
        setEnabled(btnNext, true);
        setEnabledSymbolButtons(false);
    }

    private Dialog getGameEndDialog(int level) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setMessage(String.format(res.getString(R.string.game_end_message), level));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });
        return builder.create();
    }

    private Dialog getLevelCompleteDialog(int level) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setMessage(String.format(res.getString(R.string.level_complete_message), level));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });
        return builder.create();
    }

    private void showSymbols(final Iterable<Symbol> symbols) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // show the symbols
                for (Symbol symbol : symbols) {
                    symbolsView.updateDisplay(symbol);
                }

                // reenable the symbol buttons
                setEnabledSymbolButtons(true);
            }
        }).start();
    }

    private synchronized void setEnabled(final View view, final boolean state) {
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(state);
            }
        });
    }

    private void setEnabledSymbolButtons(boolean state) {
        setEnabled(btnTopRight, state);
        setEnabled(btnBottomLeft, state);
        setEnabled(btnBottomRight, state);
        setEnabled(btnTopLeft, state);
    }

}
