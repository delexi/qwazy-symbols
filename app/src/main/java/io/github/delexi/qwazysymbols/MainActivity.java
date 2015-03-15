package io.github.delexi.qwazysymbols;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.delexi.qwazysymbols.core.SymbolLogic;
import io.github.delexi.qwazysymbols.core.SymbolLogicImpl;
import io.github.delexi.qwazysymbols.view.ConnectionMachineView;


public class MainActivity extends Activity {

    private static SymbolLogic theSymbolLogic;
    private static io.github.delexi.qwazysymbols.view.View theSymbolsView;

    public static SymbolLogic getTheSymbolLogic() {
        return theSymbolLogic;
    }
    public static io.github.delexi.qwazysymbols.view.View getTheSymbolsView() {
        return theSymbolsView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtGameRules = (TextView) findViewById(R.id.txtGameRules);
        txtGameRules.setMovementMethod(new ScrollingMovementMethod());

        Button btnStartGame = (Button) findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theSymbolsView == null) {
                    theSymbolsView = ConnectionMachineView.instantiate(MainActivity.this);
                    if (theSymbolsView == null) {
                        getConnectionFailedDialog().show();
                        return;
                    }
                }

                theSymbolLogic = new SymbolLogicImpl();

                Intent intent = new Intent(MainActivity.this, SymbolInputActivity.class);
                startActivity(intent);
            }
        });
    }

    private Dialog getConnectionFailedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.id.connection_failed);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });
        return builder.create();
    }
}
