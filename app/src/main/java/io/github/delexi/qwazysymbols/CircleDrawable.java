package io.github.delexi.qwazysymbols;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

import static android.graphics.Paint.Style.FILL;

/**
 * Created by delexi on 11/26/14.
 */
public class CircleDrawable extends Drawable {
    private Paint paint;

    public CircleDrawable() {
        paint = new Paint();
        paint.setColor(0xffffff);
        paint.setStyle(FILL);
        paint.setStrokeWidth(2);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(getClass().getSimpleName(), "Draw a circle");
        canvas.drawCircle(50, 50, 50, paint);
    }

    @Override
    public void setAlpha(int i) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
