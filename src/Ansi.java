/**
 * Functions to interact with the terminal using ANSI escape codes.
 *
 * @author Ismael Trentin
 */
public class Ansi {

    public static final String CLEAR = "\u001B[2J";
    public static final String CLEAR_LINE = "\u001B[2K";
    public static final String CLEAR_LINE_FROM_CURSOR = "\u001B[0K";
    public static final String CLEAR_LINE_UP_TO_CURSOR = "\u001B[1K";
    public static final String TOP_LEFT = "\u001B[H";
    public static final String CURSOR_UP = "\u001B[1A";
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String DEFAULT_FG = "\u001B[39m";
    public static final String DEFAULT_BG = "\u001B[49m";
    public static final String BOLD = "\033[1m";

    static String fg(String text, int r, int g, int b) {
        r = Math.min(r, 255);
        g = Math.min(g, 255);
        b = Math.min(b, 255);
        return String.format("\033[38;2;%d;%d;%dm%s%s", r, g, b, text, RESET);
    }

    static int lengthIgnoreAnsi(String text) {
        String ansiRegex = "\\u001B\\[[;\\d]*[ -/]*[@-~]";

        // clone string so that we
        // do not change the original
        String copy = new String(text);

        return copy.replaceAll(ansiRegex, "").length();
    }

    static String reset(String text) {
        return RESET + text + RESET;
    }

    static String black(String text) {
        return BLACK + text + RESET;
    }

    static String red(String text) {
        return RED + text + RESET;
    }

    static String green(String text) {
        return GREEN + text + RESET;
    }

    static String yellow(String text) {
        return YELLOW + text + RESET;
    }

    static String blue(String text) {
        return BLUE + text + RESET;
    }

    static String purple(String text) {
        return PURPLE + text + RESET;
    }

    static String cyan(String text) {
        return CYAN + text + RESET;
    }

    static String white(String text) {
        return WHITE + text + RESET;
    }

    static String bold(String text) {
        return BOLD + text + RESET;
    }

    static void cursorTo(int row, int col) {
        System.out.printf("\u001B[%d;%dH", row, col);
    }

    static void clearScreen() {
        System.out.println(CLEAR);
        cursorTo(0, 0);
    }

    static void clearLine() {
        System.out.print(CLEAR_LINE);
        System.out.print('\r');
    }

    static void clearLines(int n) {
        for (int i = 0; i < n; i++) {
            clearLine();
            System.out.print(CURSOR_UP);
        }
    }

    static String centerLine(String line, int width) {
        int xPadding = (width - lengthIgnoreAnsi(line)) / 2;

        return String.format("%" + xPadding + "s%s%" + xPadding + "s\n", "", line, "");
    }

    static String centerText(String text, int width, int height) {
        String out = "";
        String[] lines = text.split("\n");

        int yPadding = (height - lines.length) / 2;
        for (int i = 0; i < yPadding * 2 + 1; i++) {
            if (i == yPadding) {
                for (String line : lines) {
                    out += centerLine(line, width);
                }
                continue;
            }
            out += "\n";
        }

        return out;
    }

    static void printCentered(String text, int width, int height) {
        System.out.println(centerText(text, width, height));
    }
}
