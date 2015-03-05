package io.github.delexi.qwazysymbols;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;

import static android.graphics.Paint.Style.FILL;


public class SymbolInputActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_symbol_input);
        ImageButton topLeft = (ImageButton) findViewById(R.id.btnTopLeft);
        topLeft.setImageResource(R.drawable.circle_green_200x200);
        topLeft.invalidate();

        ImageButton topRight = (ImageButton) findViewById(R.id.btnTopRight);
        topRight.setImageResource(R.drawable.rect_blue_200x200);
        topRight.invalidate();

        ImageButton bottomLeft = (ImageButton) findViewById(R.id.btnBottomLeft);
        bottomLeft.setImageResource(R.drawable.cross_red_200x200);
        bottomLeft.invalidate();

        ImageButton bottomRight = (ImageButton) findViewById(R.id.btnBottomRight);
        bottomRight.setImageResource(R.drawable.triangle_yellow_200x200);
        bottomRight.invalidate();
    }


}
