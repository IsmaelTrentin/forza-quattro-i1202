/**
 * 'Forza Quattro' game.
 *
 * @author Ivan Paljevic
 * @author Ismael Trentin
 * @version 2025.01.06
 */
public class ForzaQuattro {

    public static void main(String[] args) {
        GestioneInput input = new GestioneInput();
        StartMenu menu = new StartMenu();
        Partita gm = new Partita(input);

        menu.show(input); // blocking
        gm.start(); // blocking

        input.rilascia();
    }
}
