/**
 * Functions to interact with the terminal using ANSI escape codes.
 *
 * @author Ismael Trentin
 * @version 2025.01.06
 */
public class Ansi {

    /**
     * Clear terminal code.
     */
    public static final String CLEAR = "\u001B[2J";

    /**
     * Clear the current line code.
     */
    public static final String CLEAR_LINE = "\u001B[2K";

    /**
     * Move cursor to top left code.
     */
    public static final String TOP_LEFT = "\u001B[H";

    /**
     * Move cursor to bottom left code.
     */
    public static final String BOTTOM_LEFT = "\033[500;1H";

    /**
     * Move cursor up one row code.
     */
    public static final String CURSOR_UP = "\u001B[1A";

    /**
     * Reset text modifiers code.
     */
    public static final String RESET = "\u001B[0m";

    /**
     * Black text code.
     */
    public static final String BLACK = "\u001B[30m";

    /**
     * Red text code.
     */
    public static final String RED = "\u001B[31m";

    /**
     * Green text code.
     */
    public static final String GREEN = "\u001B[32m";

    /**
     * Yellow text code.
     */
    public static final String YELLOW = "\u001B[33m";

    /**
     * Blue text code.
     */
    public static final String BLUE = "\u001B[34m";

    /**
     * Purple text code.
     */
    public static final String PURPLE = "\u001B[35m";

    /**
     * Cyan text code.
     */
    public static final String CYAN = "\u001B[36m";

    /**
     * White text code.
     */
    public static final String WHITE = "\u001B[37m";

    /**
     * Default text foreground color code.
     */
    public static final String DEFAULT_FG = "\u001B[39m";

    /**
     * Bold text modifier.
     */
    public static final String BOLD = "\033[1m";

    /**
     * Italic text modifier.
     */
    public static final String ITALIC = "\033[3m";

    /**
     * Applies a foreground color with components <code>[r,g,b]</code> to
     * <code>text</code>.
     * 
     * @param text the text on which to apply the color
     * @param r    red component
     * @param g    green component
     * @param b    blue component
     * @return the colored text
     */
    static String fg(String text, int r, int g, int b) {
        r = Math.min(r, 255);
        g = Math.min(g, 255);
        b = Math.min(b, 255);
        return String.format("\033[38;2;%d;%d;%dm%s%s", r, g, b, text, RESET);
    }

    /**
     * Resets text modifiers.
     * 
     * @param text target text
     * @return reset text
     */
    static String reset(String text) {
        return RESET + text + RESET;
    }

    /**
     * Colors <code>text</code> to black.
     * 
     * @param text target text
     * @return colored text
     */
    static String black(String text) {
        return BLACK + text + RESET;
    }

    /**
     * Colors <code>text</code> to red.
     * 
     * @param text target text
     * @return colored text
     */
    static String red(String text) {
        return RED + text + RESET;
    }

    /**
     * Colors <code>text</code> to green.
     * 
     * @param text target text
     * @return colored text
     */
    static String green(String text) {
        return GREEN + text + RESET;
    }

    /**
     * Colors <code>text</code> to yellow.
     * 
     * @param text target text
     * @return colored text
     */
    static String yellow(String text) {
        return YELLOW + text + RESET;
    }

    /**
     * Colors <code>text</code> to blue.
     * 
     * @param text target text
     * @return colored text
     */
    static String blue(String text) {
        return BLUE + text + RESET;
    }

    /**
     * Colors <code>text</code> to purple.
     * 
     * @param text target text
     * @return colored text
     */
    static String purple(String text) {
        return PURPLE + text + RESET;
    }

    /**
     * Colors <code>text</code> to cyan.
     * 
     * @param text target text
     * @return colored text
     */
    static String cyan(String text) {
        return CYAN + text + RESET;
    }

    /**
     * Colors <code>text</code> to white.
     * 
     * @param text target text
     * @return colored text
     */
    static String white(String text) {
        return WHITE + text + RESET;
    }

    /**
     * Applies the bold modifier to <code>text</code>.
     * 
     * @param text target text
     * @return bold text
     */
    static String bold(String text) {
        return BOLD + text + RESET;
    }

    /**
     * Applies the italic modifier to <code>text</code>.
     * 
     * @param text target text
     * @return italic text
     */
    static String italic(String text) {
        return ITALIC + text + RESET;
    }

    /**
     * Moves the cursor to <code>(row, col)</code>.
     * 
     * @param row terminal row
     * @param col terminal column
     */
    static void cursorTo(int row, int col) {
        System.out.printf("\u001B[%d;%dH", row, col);
    }

    /**
     * Clears the terminal screen.
     */
    static void clearScreen() {
        System.out.println(CLEAR);
        cursorTo(0, 0);
    }

    /**
     * Clears the current cursor line.
     */
    static void clearLine() {
        System.out.print(CLEAR_LINE);
        System.out.print('\r');
    }
}
