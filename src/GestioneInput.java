import java.util.Scanner;

/**
 * Handles requesting input using a Scanner object.
 *
 * @author Ismael Trentin
 * @version 2025.01.06
 * @see Scanner
 */
public class GestioneInput {

    /**
     * The scanner responsible for handling input.
     */
    Scanner input;

    /**
     * Creates a new instance of `GestioneInput` instantiating a new `Scanner`
     * that reads from `System.in`.
     *
     * @see Scanner
     * @see System#in
     */
    public GestioneInput() {
        this.input = new Scanner(System.in);
    }

    /**
     * Closes the scanner and releases all related system resources.
     */
    void rilascia() {
        input.close();
    }

    /**
     * Asks for a string and waits for input.
     * This function blocks until it receives an input.
     *
     * @param prompt what message to print before waiting for input
     * @return the string inputted by the user.
     */
    String askString(String prompt) {
        String str;

        System.out.print(prompt);
        while (!input.hasNextLine() || (str = input.nextLine()).isBlank()) {
            System.out.print(Ansi.CURSOR_UP);
            Ansi.clearLine();
            System.out.print("(invalido) " + prompt);
            // input.nextLine();
        }

        return str.trim();
    }

    /**
     * Asks for an integer that is in the range [min, max]. If an out of bounds
     * input is provided, an error message is printed and input is requested once
     * again.
     * This function blocks until a <b>valid</b> input is received.
     *
     * @param prompt what message to print before asking for input
     * @param min    the valid range minimum, included
     * @param max    the valid range maximum, included
     * @return the int inputted by the user
     */
    int askRangedInt(String prompt, int min, int max) {
        int n;

        System.out.print(prompt);
        while (!input.hasNextInt() || (n = input.nextInt()) > max || n < min) {
            System.out.print(Ansi.CURSOR_UP);
            Ansi.clearLine();
            System.out.printf("(invalido, deve essere tra %s e %s) %s", min, max, prompt);
            input.nextLine();
        }

        // clear scanner buffer
        input.nextLine();

        return n;
    }

    /**
     * Asks for an odd integer that is in the range [1, 15]. If an out of bounds
     * input or even number is provided, an error message is printed and input is
     * requested once again.
     * This function blocks until a <b>valid</b> input is received.
     *
     * @param prompt what message to print before asking for input
     * @return the int inputted by the user.
     */
    int askNumberOfMatches(String prompt) {
        int n;

        System.out.print(prompt);
        while (!input.hasNextInt() || (n = input.nextInt()) > 15 || n < 1 || n % 2 == 0) {
            System.out.print(Ansi.CURSOR_UP);
            Ansi.clearLine();
            System.out.printf("(invalido, deve essere dispari e tra %s e %s) %s", 1, 15, prompt);
            input.nextLine();
        }

        return n;
    }

    /**
     * Prints a dedicated menu to select between the provided icons.
     *
     * @param prompt what message to print before printing the menu
     * @icons icons the available icons
     * @return the selected icon
     */
    String askPlayerIcon(String prompt, String[] icons) {
        int iconIdx = -1;

        // ansiClearScreen();
        System.out.println(prompt);
        for (int i = 0; i < icons.length; i++) {
            System.out.println((i + 1) + ") " + icons[i]);
        }

        boolean isBadOpt = false;
        do {

            if (isBadOpt) {
                System.out.print(Ansi.CURSOR_UP);
                Ansi.clearLine();
                System.out.print("(opzione invalida) ");
            }

            isBadOpt = iconIdx <= 0 || iconIdx > icons.length;
        } while ((iconIdx = askRangedInt("seleziona 1-" + icons.length + ": ", 1, icons.length)) <= 0
                || iconIdx > icons.length);

        return icons[iconIdx - 1];
    }

    /**
     * Reads the next byte in raw mode. If raw mode is not supported it defaults to
     * a {@link Scanner#nextLine()} and returns the code of the first character in
     * the line, -1 if the line is empty.
     * 
     * @return the next byte in raw mode.
     */
    int read() {
        if (!this.isRawModeSupported()) {
            return this.readNormalMode();
        }

        return this.readRawMode();
    }

    /**
     * Reads the next byte in raw mode.
     * 
     * @return the byte read
     */
    int readRawMode() {
        if (!this.isRawModeSupported()) {
            System.err.println("error: raw mode is not supported on this os");
            return -1;
        }

        try {
            this.setUnixRawMode();
            int b = System.in.read();
            this.restoreUnixTerminal();
            return b;
        } catch (Exception e) {
            System.err.println("IO error: failed to read stdin");
            return -1;
        }
    }

    /**
     * Tries to emulate {@link GestioneInput#readRawMode()}.
     * 
     * @see GestioneInput#read()
     * @return the code of the first character in the next line, -1 if line is null.
     */
    int readNormalMode() {
        String line = this.input.nextLine();
        if (line != null && line.length() > 0) {
            return line.charAt(0);
        }
        return -1;
    }

    /**
     * Returns true if the current terminal supports raw mode.
     * 
     * @return true if terminal supports raw mode, false otherwise.
     */
    boolean isRawModeSupported() {
        String osName = System.getProperty("os.name").toLowerCase();

        return osName.contains("mac") || osName.contains("nix") || osName.contains("nux");
    }

    /**
     * Puts the terminal into raw mode.
     */
    void setUnixRawMode() {
        if (!this.isRawModeSupported()) {
            System.err.println("error: raw mode is not supported on this os");
            return;
        }

        // check man for actual use:
        // save_state=$(stty -g)
        // stty raw
        // ...
        // stty "$save_state"

        try {
            Runtime.getRuntime().exec(new String[] { "sh", "-c", "stty raw -echo </dev/tty" }).waitFor();
        } catch (Exception e) {
            System.err.println("error: failed to set raw mode for unix terminal");
            e.printStackTrace();
        }
    }

    /**
     * Restores the terminal into normal mode.
     */
    void restoreUnixTerminal() {
        if (!this.isRawModeSupported()) {
            System.err.println("error: raw mode is not supported on this os. could not reset.");
            return;
        }

        try {
            Runtime.getRuntime().exec(new String[] { "sh", "-c", "stty -raw echo </dev/tty" }).waitFor();
            // System.out.println("\nTerminal restored to normal mode.");
        } catch (Exception e) {
            System.err.println("error: failed to restore unix terminal");
            e.printStackTrace();
        }
    }
}
