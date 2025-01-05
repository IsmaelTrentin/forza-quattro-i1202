/**
 *
 */
public class ForzaQuattro {

    public static void main(String[] args) {
        GestioneInput input = new GestioneInput();
        StartMenu menu = new StartMenu();
        Partita gm = new Partita();

        menu.show(input); // blocking
        gm.start(input);

        input.rilascia();
    }
}
