package io.github.delexi.qwazysymbols.view;

import android.content.Context;
import android.util.Log;

import com.example.connectionmachineexample.LEDMatrixBTConn;

import java.util.Arrays;

import io.github.delexi.qwazysymbols.core.Symbol;

public class ConnectionMachineView implements View {

    // name of the CM
//    protected static final String REMOTE_BT_DEVICE_NAME = "ledpi-teco";
    protected static final String REMOTE_BT_DEVICE_NAME = "delexi-T420s-0";

    private static final String DEBUG_TAG = ConnectionMachineView.class.toString();

    // width and height of CM
    private static final int X_SIZE = 24;
    private static final int Y_SIZE = 24;

    // color mode of CM, only 0 is supported.
    private static final int COLOR_MODE = 0;

    // name of this app as shown to CM
    private static final String APP_NAME = "QwazySymbols";

    private byte[][] content;

    private LEDMatrixBTConn btConn;

    private ConnectionMachineView(LEDMatrixBTConn btConn) {
        this.btConn = btConn;
        content = initialiseView(X_SIZE, Y_SIZE);
        for (int i = 0; i < content.length; i++) {
            Log.d(DEBUG_TAG, "Length [" + i + "]: " + content[i].length);
        }
    }

    public static ConnectionMachineView instantiate(Context context) {
        LEDMatrixBTConn btConn = new LEDMatrixBTConn(
                context, REMOTE_BT_DEVICE_NAME, X_SIZE, Y_SIZE, COLOR_MODE, APP_NAME);

        if (// get the bluetooth adapter
            btConn.prepare() &&
            // check if this device and the CM are paired
            btConn.checkIfDeviceIsPaired() &&
            // setup the connection streams, send handshake package and handle response
            btConn.connect()) {
            // successfully established connection - return an instance of this view
            return new ConnectionMachineView(btConn);
        } else {
            // connection not established - return null
            return null;
        }
    }

    @Override
    public int maximumSymbolsPerSecond() {
        return this.btConn.getMaxFPS();
    }

    private byte[][] initialiseView(int width, int height) {
        CliView cliView = new CliView(width, height);
        byte[][] ret = new byte[Symbol.values().length][width * height];
        for (Symbol symbol : Symbol.values()) {
            byte[] unshifted = stringToByteArray(cliView.symbolToString(symbol));
            ret[symbol.ordinal()] = shiftToQuadrant(unshifted, symbol);
        }
        return ret;
    }

    private byte[] stringToByteArray(String symbol) {
        String[] lines = symbol.split(System.lineSeparator());
        final int height = lines.length;
        final int width = lines[0].length();
        byte[] ret = new byte[height * width];
        char fill = 'o';
        for (int lineIdx = 0; lineIdx < height; lineIdx++) {
            char[] chars = new char[width];
            lines[lineIdx].getChars(0, width, chars, 0);
            for (int charIdx = 0; charIdx < width; charIdx++) {
                ret[charIdx + lineIdx * width] = (byte) (chars[charIdx] == fill ? 255 : 0);
            }
        }
        Log.d(this.getClass().toString(), Integer.toString(ret.length));
        return ret;
    }

    private byte[] merge(byte[] left, byte[] right) {
        // intersperse 12 elements from left and 12 elements from right, then start over, till end
        byte[] ret = new byte[12 * 24];
        for (int i = 0; i < 12; i++) {
            System.arraycopy(left, i * 12, ret, i * 24, 12);
            System.arraycopy(right, i * 12, ret, i * 24 + 12, 12);
        }
        return ret;
    }

    private byte[] shiftToQuadrant(byte[] unshifted, Symbol symbol) {
        byte shifted[] = new byte[24 * 24];
        byte empty[] = new byte[12 * 12];
        Arrays.fill(empty, 0, 12 * 12, (byte) 0);
        byte[] leftColumn = merge(unshifted, empty);
        byte[] rightColumn = merge(empty, unshifted);

        switch(symbol) {
            case CIRCLE:
                System.arraycopy(leftColumn, 0, shifted, 0, leftColumn.length);
                Arrays.fill(shifted, 12 * 12 * 2, 12 * 12 * 4, (byte) 0);
                break;
            case SQUARE:
                System.arraycopy(rightColumn, 0, shifted, 0, rightColumn.length);
                Arrays.fill(shifted, 12 * 12 * 2, 12 * 12 * 4, (byte) 0);
                break;
            case CROSS:
                Arrays.fill(shifted, 12 * 12 * 0, 12 * 12 * 2, (byte) 0);
                System.arraycopy(leftColumn, 0, shifted, 12 * 12 * 2, leftColumn.length);
                break;
            case TRIANGLE:
                Arrays.fill(shifted, 12 * 12 * 0, 12 * 12 * 2, (byte) 0);
                System.arraycopy(rightColumn, 0, shifted, 12 * 12 * 2, rightColumn.length);
                break;
            case EMPTY:
                // just override the input and fill with 0s
                Arrays.fill(shifted, 0, 12 * 12 * 4, (byte) 0);
                break;
        }
        return shifted;
    }

    @Override
    public void updateDisplay(Symbol currentSymbol) {
        boolean ret = btConn.write(content[currentSymbol.ordinal()]);
        Log.d(DEBUG_TAG,
                "Write successful (" + currentSymbol +"): " + Boolean.toString(ret));
        sleep(600);
        btConn.write(content[Symbol.EMPTY.ordinal()]);
        sleep(400);
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
