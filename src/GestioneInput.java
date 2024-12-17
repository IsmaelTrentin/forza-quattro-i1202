import java.util.Scanner;

/**
 * Handles requesting input using a Scanner object.
 *
 * @author Ismael Trentin
 * @version 2024.12.14
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
            System.out.print("(invalid) " + prompt);
            // input.nextLine();
        }

        return str.trim();
    }

    /**
     * Asks for an integer and waits for input.
     * This function blocks until it receives an input.
     *
     * @param prompt what message to print before asking for input
     * @return the int inputted by the user.
     */
    int askInt(String prompt) {
        int n;

        System.out.print(prompt);
        // TODO: fix stupid workaround to use shortcircuit without condition.
        while (!input.hasNextInt() || (n = input.nextInt()) != n) {
            System.out.print(Ansi.CURSOR_UP);
            Ansi.clearLine();
            System.out.print("(invalid) " + prompt);
            input.nextLine();
        }

        // clear scanner buffer
        input.nextLine();
        return n;
    }

    /**
     * Asks for a positive integer. If a negative input is provided,
     * an error message is printed and input is requested once again.
     * This function blocks until a <b>valid</b> input is received.
     *
     * @param prompt what message to print before asking for input
     * @return the int inputted by the user.
     */
    int askPositiveInt(String prompt) {
        int n;

        System.out.print(prompt);
        while (!input.hasNextInt() || (n = input.nextInt()) <= 0) {
            System.out.print(Ansi.CURSOR_UP);
            Ansi.clearLine();
            System.out.print("(invalid) " + prompt);
            input.nextLine();
        }

        // clear scanner buffer
        input.nextLine();
        return n;
    }
}
