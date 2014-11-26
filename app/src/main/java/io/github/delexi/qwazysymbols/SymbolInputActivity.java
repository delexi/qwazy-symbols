package io.github.delexi.qwazysymbols;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.ImageButton;


public class SymbolInputActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_symbol_input);
        ImageButton topLeft = (ImageButton) findViewById(R.id.btnTopLeft);
        topLeft.setImageDrawable(new CircleDrawable());
        topLeft.invalidate();
    }
}
