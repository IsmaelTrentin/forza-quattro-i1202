import java.util.Scanner;

/**
 *
 */
public class ForzaQuattro {

    public static void main(String[] args) {
        GestioneInput input = new GestioneInput();
        Partita gm = new Partita();

        gm.start(input);

        input.rilascia();
    }
}
