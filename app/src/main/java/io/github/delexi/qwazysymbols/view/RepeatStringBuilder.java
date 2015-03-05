package io.github.delexi.qwazysymbols.view;

public class RepeatStringBuilder {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static RepeatStringBuilder repeater(StringBuilder sb) {
        return new RepeatStringBuilder(sb);
    }

    private StringBuilder delegate;

    public RepeatStringBuilder(StringBuilder delegate) {
        this.delegate = delegate;
    }

    public RepeatStringBuilder append(String s) {
        delegate.append(s);
        return this;
    }

    public RepeatStringBuilder appendln(String s) {
        delegate.append(s);
        delegate.append(LINE_SEPARATOR);
        return this;
    }

    public RepeatStringBuilder append(String s, int n) {
        for (int i = 0; i < n; i++) {
            delegate.append(s);
        }
        return this;
    }

    public RepeatStringBuilder appendln(String s, int n) {
        return append(s, n).newline();
    }

    public RepeatStringBuilder newline() {
        delegate.append(LINE_SEPARATOR);
        return this;
    }

    public StringBuilder getDelegate() {
        return delegate;
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
