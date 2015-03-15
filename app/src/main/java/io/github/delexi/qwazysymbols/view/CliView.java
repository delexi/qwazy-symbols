package io.github.delexi.qwazysymbols.view;

import io.github.delexi.qwazysymbols.core.Symbol;

import static io.github.delexi.qwazysymbols.view.RepeatStringBuilder.repeater;

public class CliView implements View {

    private final String SQUARE;
    private final String CIRCLE;
    private final String CROSS;
    private final String TRIANGLE;
    private final String EMPTY;

    private String fill = "o";
    private String free = " ";

    public CliView(int width, int height) {
        if (width < 10 || height < 10) {
            throw new IllegalArgumentException("width and height must at least be 10");
        }

        final int symbolWidth = width / 2;
        final int symbolHeight = height / 2;
        SQUARE = buildRectangle(symbolWidth, symbolHeight);
        CIRCLE = buildCircle(symbolWidth, symbolHeight);
        CROSS = buildCross(symbolWidth, symbolHeight);
        TRIANGLE = buildTriangle(symbolWidth, symbolHeight);
        EMPTY = buildEmpty(symbolWidth, symbolHeight);
    }

    private String buildCircle(int width, int height) {
        // to do: take width and height into account
        String first = repeater(new StringBuilder(width))
                .append(free, 4).append(fill, 4).appendln(free, 4).toString();
        String second = repeater(new StringBuilder(width))
                .append(free, 2).append(fill, 8).appendln(free, 2).toString();
        String third = repeater(new StringBuilder(width))
                .append(free, 1).append(fill, 10).appendln(free, 1).toString();
        String middle = repeater(new StringBuilder(width)).appendln(fill, width).toString();
        return repeater(new StringBuilder(width))
                .append(first).append(second).append(third, 2)
                .append(middle, 4)
                .append(third, 2).append(second).append(first)
                .toString();
    }

    private String buildRectangle(int width, int height) {
        String row = repeater(new StringBuilder(width)).appendln(fill, width).toString();

        //                               +1 to incorporate the newlines
        return repeater(new StringBuilder((width + 1) * height))
                .append(row, height).toString();
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
            .append(upperLowerLine, freeHorizontalSpace).toString();
    }

    private String buildTriangle(int width, int height) {
        RepeatStringBuilder triangle = repeater(new StringBuilder(width));
        int outer = width / 2 - 1;
        int inner = 1;
        int currentHeight = 0;
        while (outer >= 0 && currentHeight <= height) {
            String line = repeater(new StringBuilder(width))
                    .append(free, outer).append(fill, inner).append(free, outer)
                    // right padding, if the upper corner is not in the middle
                    .append(free, width - outer * 2 - inner).toString();
            triangle.appendln(line);
            triangle.appendln(line);

            outer -= 1;
            inner += 2;
            currentHeight += 2;
        }
        return triangle.toString();
    }

    private String buildEmpty(int width, int height) {
        String emptyRow = repeater(new StringBuilder(width + 1))
                .appendln(free, width).toString();
        return repeater(new StringBuilder((width + 1) * height))
                .append(emptyRow, height).toString();
    }

    @Override
    public void updateDisplay(Symbol currentSymbol) {
        System.out.print(symbolToString(currentSymbol));
    }

    @Override
    public int maximumSymbolsPerSecond() {
        return 5;
    }

    public String symbolToString(Symbol symbol) {
        switch (symbol) {
            case CIRCLE: return CIRCLE;
            case SQUARE: return SQUARE;
            case CROSS: return CROSS;
            case TRIANGLE: return TRIANGLE;
            case EMPTY: return EMPTY;
        }
        return "";
    }
}
