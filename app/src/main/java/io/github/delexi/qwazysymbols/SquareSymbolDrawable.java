package io.github.delexi.qwazysymbols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

/**
 * Created by delexi on 11/26/14.
 */
public class SquareSymbolDrawable extends View {
    private ShapeDrawable shapeDrawable;

    public SquareSymbolDrawable(Context context) {
        super(context);
        int x = 10;
        int y = 10;
        shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.getPaint().setColor(0x0000ff);

        shapeDrawable.setBounds(x, y, getWidth() - (2 * x), getHeight() - (2 * y));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shapeDrawable.draw(canvas);
    }
}
