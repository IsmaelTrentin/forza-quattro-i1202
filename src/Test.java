/**
 * Test
 */
public class Test {

    public static void main(String[] args) {
        Ansi.clearScreen();
        String header = Ansi.red("FORZA QUATTRO");
        String text = header
                + "\n\n"
                + Ansi.cyan("->")
                + Ansi.yellow(" premi per giocare ")
                + Ansi.cyan("<-\n");
        Ansi.printCentered(text, 137, 40);

        GestioneInput input = new GestioneInput();
        System.out.println(input.askNumberOfMatches("numero match: "));
    }
}
