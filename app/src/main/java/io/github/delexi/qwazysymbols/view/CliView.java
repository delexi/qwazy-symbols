package io.github.delexi.qwazysymbols.view;

import io.github.delexi.qwazysymbols.core.Symbol;

import static io.github.delexi.qwazysymbols.view.RepeatStringBuilder.repeater;

public class CliView implements View {

    private final String SQUARE;
    private final String CIRCLE;
    private final String CROSS;
    private final String TRIANGLE;

    private String fill = "o";
    private String free = " ";

    public CliView(int width, int height) {
        if (width < 10 || height < 10) {
            throw new IllegalArgumentException("width and height must at least be 10");
        }

        final int symbolWidth = width / 2;
        final int symbolHeight = height / 2;
        SQUARE = buildRectangle(symbolWidth, symbolHeight);
        CIRCLE = "";
        CROSS = buildCross(symbolWidth, symbolHeight);
        TRIANGLE = "";
    }

    private String buildRectangle(int width, int height) {
        String topBottomRow = repeater(new StringBuilder(width))
            .appendln(fill, width).toString();

        String middleRow = repeater(new StringBuilder(width))
            .append(fill).append(free, width - 2).appendln(fill).toString();

        //                               +1 to incorporate the newlines
        return repeater(new StringBuilder((width + 1) * height))
                .append(topBottomRow)
                .append(middleRow, height - 2)
                .appendln(topBottomRow).toString();
    }

    private String buildCross(int width, int height) {
        final int strokeWidth = width / 3 > 1 ? width / 3 : 1;
        final int strokeHeight = height / 3 > 1 ? height / 3 : 1;
        final int freeVerticalSpace = (width - strokeWidth) / 2;
        final int freeHorizontalSpace = (height - strokeHeight) / 2;

        String upperLowerLine = repeater(new StringBuilder(width))
            .append(free, freeVerticalSpace)
            .append(fill, strokeWidth)
            .appendln(free, freeVerticalSpace).toString();

        String middleLine = repeater(new StringBuilder(width))
            .appendln(fill, width).toString();

        return repeater(new StringBuilder((width + 1) * height))
            .append(upperLowerLine, freeHorizontalSpace)
            .append(middleLine, strokeHeight)
            .appendln(upperLowerLine, freeHorizontalSpace).toString();
    }

    private String buildTriangle(int width, int height) {
        String bottomLine = repeater(new StringBuilder(width)).appendln(fill, width).toString();
        return bottomLine;
    }

    @Override
    public void updateDisplay(Symbol currentSymbol) {
        System.out.print(symbolToString(currentSymbol));
    }

    private String symbolToString(Symbol symbol) {
        switch (symbol) {
            case SQUARE: return SQUARE;
            case CROSS: return CROSS;
            default: return "Not yet implemented: " + symbol + "\n";
        }
    }
}
