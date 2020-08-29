package model.export;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public final class ExpCSV {

    private static final char DEFAULT_SEPARATOR = ',';

    private ExpCSV() {
        return;
    }

    public static void writeLine(final Writer w, final List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(final Writer w, final List<String> values, final char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(final String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(final Writer w, final List<String> values, final char separators, final char customQuote) throws IOException {

        boolean first = true;
        char sep = separators;

        //default customQuote is empty

        if (sep == ' ') {
            sep = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(sep);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());

    }

    /*
    public void name() {
    }
    */
}
